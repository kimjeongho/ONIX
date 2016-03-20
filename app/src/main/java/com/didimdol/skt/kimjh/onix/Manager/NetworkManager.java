package com.didimdol.skt.kimjh.onix.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentResult;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistDetailResult;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListResult;
import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentReadResult;
import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentReadSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentResult;
import com.didimdol.skt.kimjh.onix.DataClass.BoardTotalResult;
import com.didimdol.skt.kimjh.onix.DataClass.BoardTotalSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.BoardWriteResult;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceMinusResult;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceResult;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.ChoicePlusResult;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountListResult;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountListSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.JoinResult;
import com.didimdol.skt.kimjh.onix.DataClass.LoginFacebookResult;
import com.didimdol.skt.kimjh.onix.DataClass.LoginResult;
import com.didimdol.skt.kimjh.onix.DataClass.LogoutResult;
import com.didimdol.skt.kimjh.onix.DataClass.LogoutSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.ShopDetailResult;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopListResult;
import com.didimdol.skt.kimjh.onix.DataClass.ShopListSuccess;
import com.didimdol.skt.kimjh.onix.MyApplication;
import com.didimdol.skt.kimjh.onix.PersistentCookieStore;
import com.didimdol.skt.kimjh.onix.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by kimjh on 2016-02-22.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    OkHttpClient mClient; // okHttpclient 가져옴
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        File cachefile = new File(context.getExternalCacheDir(),"mycache");
        if(!cachefile.exists()){
            cachefile.mkdirs();
        }

        Cache cache = new Cache(cachefile, MAX_CACHE_SIZE);
        builder.cache(cache);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        disableCertificateValidation(context, builder);

        mClient = builder.build();
    }
    static void disableCertificateValidation(Context context, OkHttpClient.Builder builder) {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.site);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            sc.init(null, tmf.getTrustManagers(), null);
            builder.sslSocketFactory(sc.getSocketFactory());
            builder.hostnameVerifier(hv);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelAll() {
        mClient.dispatcher().cancelAll();
    }

    public void cancelTag(Object tag) {
        Dispatcher dispatcher = mClient.dispatcher();
        List<Call> calls = dispatcher.queuedCalls();
        for (Call call : calls) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
        calls = dispatcher.runningCalls();
        for (Call call : calls) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }


    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);
        public void onFailure(Request request, int code, Throwable cause);
    }

    /*public interface OnResultListener<T> {
        public void onSuccess(T result);
        public void onFailure(int code);
    }*/

    private static final int MESSAGE_SUCCESS = 0;
    private static final int MESSAGE_FALURE = 1;

    static class NetworkHandler extends Handler{
        public NetworkHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CallbackObject object = (CallbackObject)msg.obj;
            Request request = object.request;
            OnResultListener listener = object.listener;
            switch (msg.what){
                case MESSAGE_SUCCESS:
                    listener.onSuccess(request,object.result);
                    break;
                case MESSAGE_FALURE:
                    listener.onFailure(request, -1, object.exception);
                    break;
            }

        }
    }

    Handler mHandler = new NetworkHandler(Looper.getMainLooper());

    static class CallbackObject<T>{
        Request request;
        T result;
        IOException exception;
        OnResultListener<T> listener;
    }
//아티스트 리스트 페이지------------------------------------------------------------------------------------------------------------------------------------
    private static final String URL_ARTIST_LIST = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/artists?page=%s";
    public Request getArtistListDataResult(Context context, int page, int condition, String search, final OnResultListener<ArtistListSuccess> listener){
        String url = null;
        url = String.format(URL_ARTIST_LIST,page);
        if (condition == 0) {
            try {
                url += "&search=" + URLEncoder.encode(search,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            url += "&condition=" + condition;
        }
        final CallbackObject<ArtistListSuccess> callbackObject = new CallbackObject<ArtistListSuccess>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ArtistListResult result = gson.fromJson(response.body().charStream(), ArtistListResult.class);
                callbackObject.result = result.successResult;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return  request;
    }
    //아티스트 리스트 페이지------------------------------------------------------------------------------------------------------------------------------------

/*
    //샵 리스트 페이지----------------------------------------------------------------------------------------------------------------------------------------

    private static final String URL_SHOP_LIST = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/shops/?page=%s&search=%s&condition=%s";
    public Request getShopTotalDataResult(Context context, int page, String search, String condition, final OnResultListener<ShopListSuccess> listener){
        String url = null;
        try {
            url = String.format(URL_SHOP_LIST,page, URLEncoder.encode(search, "utf-8"), URLEncoder.encode(condition,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final CallbackObject<ShopListSuccess> callbackObject = new CallbackObject<ShopListSuccess>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ShopListResult result = gson.fromJson(response.body().charStream(), ShopListResult.class);
                callbackObject.result = result.successResult;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    //샵 리스트 페이지----------------------------------------------------------------------------------------------------------------------------------------
*/

    //샵 리스트 페이지(post)----------------------------------------------------------------------------------------------------------------------------------------

    private static final String URL_SHOP_LIST = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/shops";
    public Request getShopTotalDataResult(Context context, int page, int condition,String search, double latitude, double longitude, final OnResultListener<ShopListSuccess> listener){
        String url = URL_SHOP_LIST;
        final CallbackObject<ShopListSuccess> callbackObject = new CallbackObject<ShopListSuccess>();

        RequestBody body = new FormBody.Builder()
                .add("page", String.valueOf(page))
                .add("search", search)
                .add("condition", String.valueOf(condition))
                .add("userLatitude", String.valueOf(latitude))
                .add("userLongitude", String.valueOf(longitude))
                .build();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ShopListResult result = gson.fromJson(response.body().charStream(), ShopListResult.class);
                callbackObject.result = result.successResult;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }
    //샵 리스트 페이지(post)----------------------------------------------------------------------------------------------------------------------------------------
    //---------게시판 댓글 보기-----------------------------------------------------------------------------------------------------------------------
    private static final String URL_BOARD_COMMENT_READ = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/boards/%s/posts/%s/replies?repliespage=%s";
    public Request getBoardCommentDataResult(Context context, int condition, int postid, int replepage, final OnResultListener<BoardCommentReadSuccess> listener){
        String url = String.format(URL_BOARD_COMMENT_READ,condition, postid, replepage);
        final CallbackObject<BoardCommentReadSuccess> callbackObject = new CallbackObject<BoardCommentReadSuccess>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String text = response.body().string();
                BoardCommentReadResult result = gson.fromJson(text, BoardCommentReadResult.class);
                callbackObject.result = result.successResult;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;
    }
    //---------게시판 댓글 보기-----------------------------------------------------------------------------------------------------------------------


    //-----------게시판 메인--------------------------------------------------------------------------------------------------------------------------
    private static final String URL_BOARD_LIST = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/boards/%s/posts?page=%s&search=%s";//
    public Request getBoardTotalDataResult(Context context,int condition, int page, String search, final OnResultListener<BoardTotalSuccess> listener){
        String url = null;

        try {
            url = String.format(URL_BOARD_LIST,condition,page,URLEncoder.encode(search,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final CallbackObject<BoardTotalSuccess> callbackObject = new CallbackObject<BoardTotalSuccess>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                BoardTotalResult result = gson.fromJson(response.body().charStream(), BoardTotalResult.class);
                callbackObject.result = result.successResult;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;
    }


    //-----------게시판 메인---------------------------------------------------------------------------------------------------------------------------
//-----------샵에서 아티스트 페이지 클릭 했을시 동작------------------------------------------------------------------------------------------
    private static final String URL_DETAIL_ARTIST = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/artists/%s";
    public Request getArtistDetailDataResult(Context context, int id, final OnResultListener<ArtistTotalData> listener){
        String url = String.format(URL_DETAIL_ARTIST,id);

        final CallbackObject<ArtistTotalData> callbackObject = new CallbackObject<ArtistTotalData>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ArtistDetailResult result = gson.fromJson(response.body().charStream(), ArtistDetailResult.class);
                callbackObject.result = result.successResult;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);

            }
        });

        return request;
    }

    //-----------샵에서 아티스트 페이지 클릭 했을시 동작------------------------------------------------------------------------------------------
    //----------아티스트에서 샵 바로가기 페이지 클릭 했을시 동작----------------------------------------------------------------------------------
    private static final String URL_DETAIL_SHOP = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/shops/%s";
    public Request getShopDetailDataResult(Context context, int id, final OnResultListener<ShopTotalData> listener){
        String url = String.format(URL_DETAIL_SHOP,id);

        final CallbackObject<ShopTotalData> callbackObject = new CallbackObject<ShopTotalData>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ShopDetailResult result = gson.fromJson(response.body().charStream(), ShopDetailResult.class);
                callbackObject.result = result.successResult;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);

            }
        });

        return request;
    }



    //----------아티스트에서 샵 바로가기 페이지 클릭 했을시 동작----------------------------------------------------------------------------------

    //----------찜목록 확인-----------------------------------------------------------------------------------------------------------------------
    private static final String URL_CHOICE_VIEW = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/jjims?page=%s";
    public Request getChoiceDataResult(Context context, int page, OnResultListener<ChoiceSuccess> listener){
        String url = String.format(URL_CHOICE_VIEW,page);

        final CallbackObject<ChoiceSuccess> callbackObject = new CallbackObject<ChoiceSuccess>();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ChoiceResult result = gson.fromJson(response.body().charStream(), ChoiceResult.class);
                callbackObject.result = result.successResult;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);

            }
        });

        return request;
    }

    //----------찜목록 확인-----------------------------------------------------------------------------------------------------------------------
    //----------할인 파우치 확인-----------------------------------------------------------------------------------------------------------------------
    private static final String URL_DISCOUNT_LIST = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/salepushes?page=%s";
    public Request getDiscountListDataResult(Context context, int page, final OnResultListener<DiscountListSuccess> listener){
        String url = String.format(URL_DISCOUNT_LIST, page);

        final CallbackObject<DiscountListSuccess> callbackObject = new CallbackObject<DiscountListSuccess>();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                DiscountListResult result = gson.fromJson(response.body().charStream(), DiscountListResult.class);
                callbackObject.result = result.successResult;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return  request;
    }
    //----------할인 파우치 확인-----------------------------------------------------------------------------------------------------------------------
   //-----------게시판 글 쓰기--------------------------------------------------------------------------------------------------------------------------
    private static final String URL_BOARD_WRITE = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/boards/%s/posts";
    public Request setBoardWrite(Context context, int condition, String title, String content, File photo, final  OnResultListener<BoardWriteResult> listener){
        String url = String.format(URL_BOARD_WRITE,condition);
        final CallbackObject<BoardWriteResult> callbackObject = new CallbackObject<BoardWriteResult>();


        MultipartBody.Builder builder =  new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content);
        if (photo != null) {
            builder.addFormDataPart("photo", photo.getName(), RequestBody.create(MediaType.parse("image/jpeg"), photo));
        }   // 객체로 요청하므로 반드시 check!
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                BoardWriteResult result = gson.fromJson(response.body().charStream(), BoardWriteResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }
   //-----------게시판 글 쓰기--------------------------------------------------------------------------------------------------------------------------

    //-----------게시판 글 쓰기(test)--------------------------------------------------------------------------------------------------------------------------
    private static final String URL_BOARD_WRITE_TEST = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/boards/%s/posts";
    public Request setBoardWriteTest(Context context, int condition, String title, String content, final  OnResultListener<BoardWriteResult> listener){
        String url = String.format(URL_BOARD_WRITE_TEST,condition);
        final CallbackObject<BoardWriteResult> callbackObject = new CallbackObject<>();

       /* RequestBody body = new FormBody.Builder()
                .add("title", title)
                .add("content", content)
                .build();*/
        MultipartBody.Builder builder =  new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content);
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                BoardWriteResult result = gson.fromJson(response.body().charStream(), BoardWriteResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }
    //-----------게시판 글 쓰기(test)--------------------------------------------------------------------------------------------------------------------------

    //----------아티스트 한줄평 쓰기-----------------------------------------------------------------------------------------------------------------------
    private static final String URL_ARTIST_COMMENT = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/artists/%s/comments";
    public Request setArtistCommentResult(Context context, int artistId, String comment, final OnResultListener<ArtistCommentResult> listener){
        String url = String.format(URL_ARTIST_COMMENT,artistId);
        final CallbackObject<ArtistCommentResult> callbackObject = new CallbackObject<ArtistCommentResult>();

        RequestBody body = new FormBody.Builder()
                /*.add("artist_id", String.valueOf(artistId))*/
                .add("content", comment)
                .build();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ArtistCommentResult result = gson.fromJson(response.body().charStream(), ArtistCommentResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;
    }

    //----------아티스트 한줄평 쓰기-----------------------------------------------------------------------------------------------------------------------

    //----------게시판 한줄평 쓰기-----------------------------------------------------------------------------------------------------------------------
    private static final String URL_BOARD_COMMENT = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/boards/%s/posts/%s/replies";
    public Request setBoardCommentResult(Context context, int condition,int boardId, String comment, final OnResultListener<BoardCommentResult> listener){
        String url = String.format(URL_BOARD_COMMENT,condition,boardId);
        final CallbackObject<BoardCommentResult> callbackObject = new CallbackObject<BoardCommentResult>();

        RequestBody body = new FormBody.Builder()
                /*.add("artist_id", String.valueOf(artistId))*/
                .add("content", comment)
                .build();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                BoardCommentResult result = gson.fromJson(response.body().charStream(), BoardCommentResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;
    }

    //----------게시판 한줄평 쓰기-----------------------------------------------------------------------------------------------------------------------
    //---------페이스북 로그인---------------------------------------------------------------------------------------------------------------------------
    private static  final String URL_FACEBOOK_LOGIN = "https://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/auth/facebook";
    public Request setLoginFacebookResult(Context context, String facebookToken, String token ,final OnResultListener<LoginFacebookResult> listener){
        String url = URL_FACEBOOK_LOGIN;
        final CallbackObject<LoginFacebookResult> callbackObject = new CallbackObject<LoginFacebookResult>();

        RequestBody body = new FormBody.Builder()
                .add("access_token", facebookToken)
                .add("registration_token", token)
                .build();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                LoginFacebookResult result = gson.fromJson(response.body().charStream(), LoginFacebookResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });
        return request;
    }

    //---------페이스북 로그인---------------------------------------------------------------------------------------------------------------------------
    //----------로그아웃--------------------------------------------------------------------------------------------------------------------------------
    private  static final String URL_LOGOUT = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/auth/logout";
    public Request setLogoutResult(Context context, final OnResultListener<LogoutResult> listener){
        String url = URL_LOGOUT;
        final CallbackObject<LogoutResult> callbackObject = new CallbackObject<LogoutResult>();

        RequestBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                LogoutResult result = gson.fromJson(response.body().charStream(), LogoutResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;

    }
    //----------로그아웃--------------------------------------------------------------------------------------------------------------------------------


    //----------로그인-----------------------------------------------------------------------------------------------------------------------------------
    private static final String URL_LOGIN = "https://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/auth/local";
    public Request setLogInResult(Context context, String email, String password, String token, int type, final OnResultListener<LoginResult> listener){
        String url = URL_LOGIN;
        final CallbackObject<LoginResult> callbackObject = new CallbackObject<LoginResult>();

        RequestBody body = new FormBody.Builder()
                .add("email_id", email)
                .add("password", password)
                .add("registration_token", token)
                .add("user_type", String.valueOf(type))
                .build();

        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();
        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                LoginResult result = gson.fromJson(response.body().charStream(), LoginResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;
    }
    //----------로그인-----------------------------------------------------------------------------------------------------------------------------------
    //----------로컬 회원가입 확인-----------------------------------------------------------------------------------------------------------------------
    private static final String URL_JOIN = "https://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/customers";
    public Request setJoinResult(Context context, String email, String password, final OnResultListener<JoinResult> listener){
        String url = URL_JOIN;
       final CallbackObject<JoinResult> callbackObject = new CallbackObject<JoinResult>();

        RequestBody body = new FormBody.Builder()
                .add("email_id",email)
                .add("password",password)
                .build();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                JoinResult result = gson.fromJson(response.body().charStream(), JoinResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;
    }
    //----------로컬 회원가입 확인-----------------------------------------------------------------------------------------------------------------------
    //----------아티스트,샵 찜하기-----------------------------------------------------------------------------------------------------------------------------------
    private static final String URL_CHOICE_PLUS = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/jjims/%s/plus";
    public Request setChoicePlusResult(Context context, int targetId, int type, final OnResultListener<ChoicePlusResult> listener){
        String url = String.format(URL_CHOICE_PLUS,targetId);
        final CallbackObject<ChoicePlusResult> callbackObject = new CallbackObject<ChoicePlusResult>();

        RequestBody body = new FormBody.Builder()
                .add("target", String.valueOf(type))
                .build();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .post(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ChoicePlusResult result = gson.fromJson(response.body().charStream(), ChoicePlusResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;

    }

    //----------찜하기-----------------------------------------------------------------------------------------------------------------------------------

    //----------아티스트,샵 찜삭제-----------------------------------------------------------------------------------------------------------------------------------
    private static final String URL_CHOICE_MINUS = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/jjims/%s/minus";
    public Request setChoiceMinusResult(Context context, int targetId, int type, final OnResultListener<ChoiceMinusResult> listener){
        String url = String.format(URL_CHOICE_MINUS,targetId);
        final CallbackObject<ChoiceMinusResult> callbackObject = new CallbackObject<ChoiceMinusResult>();

        RequestBody body = new FormBody.Builder()
                .add("target", String.valueOf(type))
                .build();
        Request request = new Request.Builder().url(url)
                .tag(context)
                .delete(body)
                .build();

        callbackObject.request = request;
        callbackObject.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbackObject.exception = e;
                Message msg = mHandler.obtainMessage(MESSAGE_FALURE, callbackObject);
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                ChoiceMinusResult result = gson.fromJson(response.body().charStream(), ChoiceMinusResult.class);
                callbackObject.result = result;
                Message msg = mHandler.obtainMessage(MESSAGE_SUCCESS, callbackObject);
                mHandler.sendMessage(msg);
            }
        });

        return request;

    }

    //----------찜삭제-----------------------------------------------------------------------------------------------------------------------------------

    //----------아티스트에서 샵페이지 클릭 했을시 동작 -------------------------------------------------------------------------------------------

   /* private static final String URL_DETAIL_SHOP = "http://ec2-52-79-117-152.ap-northeast-2.compute.amazonaws.com/shops/%s";
    public Request getShopDetailDataResult(Context context, int id, final OnResultListener<ShopTotalData> listener){
        String url =
    }*/

    //----------아티스트에서 샵페이지 클릭 했을시 동작 -------------------------------------------------------------------------------------------

    //아티스트 상세 페이지-----------------------------------------------------------------------------------------------------------------
   /* public void getArtistDetailData(int id, final OnResultListener<DetailArtistData> listener) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(null,daInitData());
            }
        }, 1000);
    }

    final static String[] NAILTYPE = {"A타입", "B타입", "C타입", "젤네일"};
    final static String[] PHOTOS = {"https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg",""};
    final static String[] NAILPRICE = {"25000", "35000", "40000", "10000"};

    private DetailArtistData daInitData() {
        DetailArtistData da = new DetailArtistData();   //artistprofile
        da.artistPhotos = Arrays.asList(PHOTOS);
        da.artistImage = "https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg";
        da.artistName = "test";
        da.shopName = "shoptest";
        da.artistContent = "content Test";
        da.nailType = new ArrayList<NailTypeData>();
        for (int i = 0; i < NAILTYPE.length; i++) {
            NailTypeData nd = new NailTypeData();   // nail type
            nd.nailPrice =  NAILPRICE[i];
            nd.nailType = NAILTYPE[i];
            da.nailType.add(nd);
        }
        da.artistComment = new ArrayList<ArtistCommentData>();
        ArtistCommentData cd = new ArtistCommentData();     //comment type
        cd.userName = "가나다";
        cd.userComment = "댓글 테스트";
        da.artistComment.add(cd);
        return da;
    }

    //아티스트 상세 페이지-----------------------------------------------------------------------------------------------------------------*/

    /*//샵 상세 페이지 ----------------------------------------------------------------------------------------------------------------------
    public void getShopDetailData(int id, final OnResultListener<DetailShopData> listener) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(null,dsInitData());
            }
        }, 1000);
    }

    final static String[] ARTISTPROFILEIMAGE = {
            "http://webneel.com/daily/sites/default/files/images/daily/05-2014/3-face-paint.jpg",
            "http://visual-makeover.com/wp-content/uploads/2011/03/Round-Face-Shape.jpg"
    };
    final static String[] PHOTOS = {"https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg",""};
    private DetailShopData dsInitData() {
        DetailShopData sd = new DetailShopData();
        sd.shopPhotos = Arrays.asList(PHOTOS);
        sd.shopTitle = "네일샵";
        for (int i = 0; i < ARTISTPROFILEIMAGE.length; i++) {
            ShopArtistListData ad = new ShopArtistListData();
            ad.artistChoice = String.valueOf(Integer.parseInt("" + 100));
            ad.artistContent = "잘하겠습니다";
            ad.artistListImage = ARTISTPROFILEIMAGE[i];
            if (i == 0) {
                ad.shopName = "/" + sd.shopTitle;
                ad.artistName = "홍길동";
                ad.artistSort = "메인 아티스트";
            } else if (i == 1) {
                ad.shopName = "/" + sd.shopTitle;
                ad.artistName = "김숙자";
                ad.artistSort = "일반 아티스트";
            }
            sd.artistListDatas.add(ad);
        }
        sd.shopTimeDatas = new ShopTimeData();
        *//*td.shopWeekDay = "09:00 ~ 21:00";
        td.shopWeekEnd = "09:00 ~ 21:00";
        td.shopWeekEtc = "연중무휴";
        td.shopAddress = "서울시 동작구 상도4동";*//*
        sd.shopTimeDatas.shopWeekDay = "09:00 ~ 21:00";
        sd.shopTimeDatas.shopWeekEnd = "09:00 ~ 21:00";
        sd.shopTimeDatas.shopWeekEtc = "연중무휴";
        sd.shopTimeDatas.shopAddress = "서울시 동작구 상도4동";
        sd.shopLocation = new ShopLocationData();
        *//*sd.shopLocation.shopLatitude = 123;
        sd.shopLocation.shopLongitude = 123;*//*
        return sd;

    }

    //샵 상세 페이지 ----------------------------------------------------------------------------------------------------------------------*/
/*//아티스트 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
    public void getArtistData(int page, final OnResultListener<List<ArtistTotalData>> listener) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(null,atInitData());
            }
        }, 1000);
    }

    static final String[] ICON_IDS = {
            "http://image.lottedfs.com/image/event/info/body_img_10086100060000800007_1.jpg",
            "http://www.manbong.com/mart7/upload/mall/%EB%9F%AD%EC%85%94%EB%A6%AC%EC%9E%A5%EC%8B%9D%20%EB%A7%88%EC%8A%A4%ED%81%AC.jpg",
            "http://dominicanhairsalondaviefl.com/wp-content/uploads/2014/11/una.jpg",
            "http://slodive.com/wp-content/uploads/2013/02/pretty-nail-designs/brown-nail.jpg"
    };

    private List<ArtistTotalData> atInitData() {
        List<ArtistTotalData> list = new ArrayList<>();
        for (int i = 0; i < ICON_IDS.length; i++) {
            ArtistTotalData ad = new ArtistTotalData();
            ad.artistName = "NICK NAME" + i;
            ad.artistDiscount = "http://image.lottedfs.com/image/event/info/body_img_10086100060000800007_1.jpg";
            ad.artistImage = ICON_IDS[i];
            ad.artistChoice = "Choice" + i;
//            ad.location = "3km";
            list.add(ad);
        }
        return list;
    }*/

    //아티스트 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
/*//샵 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
    public void getShopData(int page, final OnResultListener<List<ShopTotalData>> listener) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(null,shInitData());
            }
        }, 1000);
    }

    static final String[] ICON_IDS_SHOP = {
            "http://image.lottedfs.com/image/event/info/body_img_10086100060000800007_1.jpg",
            "http://www.manbong.com/mart7/upload/mall/%EB%9F%AD%EC%85%94%EB%A6%AC%EC%9E%A5%EC%8B%9D%20%EB%A7%88%EC%8A%A4%ED%81%AC.jpg",
            "http://dominicanhairsalondaviefl.com/wp-content/uploads/2014/11/una.jpg",
            "http://slodive.com/wp-content/uploads/2013/02/pretty-nail-designs/brown-nail.jpg"
    };

    private List<ShopTotalData> shInitData() {
        List<ShopTotalData> list = new ArrayList<>();
        for (int i = 0; i < ICON_IDS_SHOP.length; i++) {
            ShopTotalData sd = new ShopTotalData();
            sd.shopName = "NICK NAME" + i;
            sd.shopImage = ICON_IDS_SHOP[i];
            sd.shopChoice = "Choice" + i;
            sd.location = "3km";
            list.add(sd);
        }
        return list;
    }

    //샵 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------*/
/*//게시판 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
    public void getBoardData(int id, final OnResultListener<List<BoardData>> listener) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(null,bdInitData());
            }
        }, 1000);
    }

    static final String[] ICON_IDS_BOARD = {
            "http://image.lottedfs.com/image/event/info/body_img_10086100060000800007_1.jpg",
            "http://www.manbong.com/mart7/upload/mall/%EB%9F%AD%EC%85%94%EB%A6%AC%EC%9E%A5%EC%8B%9D%20%EB%A7%88%EC%8A%A4%ED%81%AC.jpg",
            "http://dominicanhairsalondaviefl.com/wp-content/uploads/2014/11/una.jpg",
            "http://slodive.com/wp-content/uploads/2013/02/pretty-nail-designs/brown-nail.jpg",
            "http://webneel.com/daily/sites/default/files/images/daily/05-2014/3-face-paint.jpg",
            "http://visual-makeover.com/wp-content/uploads/2011/03/Round-Face-Shape.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg"
    };

    static final String[] CONTENTS = {
            "이렇게 하는게 맞는 것인가?",
            "이렇게 해도 되는것 같기도하고 아닌것 같기도 하고",
            "맨땅에 헤딩하는 기분",
            "괜찮을까?! 왠지 처음부터 다시 하라고 할 삘 인데.....",
            "이렇게 하는게 되도 문제네.........",
            "알던것 까지 까먹은 듯한 기분",
            "다시 어떻게 회복하지 ㅠㅜㅠㅜ?"
    };

    private List<BoardData> bdInitData() {
        List<BoardData> list = new ArrayList<>();

        for (int i=0; i<ICON_IDS_BOARD.length; i++){
            BoardData bd = new BoardData();
            bd.boardImage = ICON_IDS_BOARD[i];
            bd.writer = "NICKNAME"+i;
            bd.boardCategory = "QnA";
            bd.boardTime = "02월 24일";
            bd.boardTitle = "TITLE" +i;
            bd.boardContent = CONTENTS[i];
            list.add(bd);

        }
        return list;
    }*/
//게시판 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
//게시판 읽기 페이지 ----------------------------------------------------------------------------------------------------------------------
/*
public void getBoardReadData(int id, final OnResultListener<List<BoardData>> listener) {
    mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            listener.onSuccess(null,brInitData());
        }
    }, 1000);
}
    final static String[] COMMENT_ID = {"김정호","김상일","이영석","박유현","최유빈"};

    private List<BoardData> brInitData() {
        List<BoardData> list = new ArrayList<>();
        BoardData br = new BoardData();
        br.boardTitle = "제목 테스트";
        br.writer = "홍길동";
        br.boardCategory = "QnA";
        br.boardTime = "05:00";
        br.boardImage = "https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg";
        br.boardContent = "sjdfhlskhdlshgklhsadlkghslkhglkashdglkdhasl;kghasl;hkdgl;akshl;hdglaksdhlgkhalsdkghadl;shkgl;hkasdl;gh";
        for (int i=0; i<COMMENT_ID.length; i++){
            BoardCommentData bc = new BoardCommentData();
            bc.userId = COMMENT_ID[i];
            bc.userComment = "댓글 테스트";
            br.boardCommentDatas.add(bc);
        }
        list.add(br);
        return list;
    }
*/

//게시판 읽기 페이지 ----------------------------------------------------------------------------------------------------------------------


//찜내역 읽기 페이지 ----------------------------------------------------------------------------------------------------------------------
/*public void getChoiceData(int id, final OnResultListener<List<ChoiceData>> listener) {
    mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            listener.onSuccess(null,chInitData());
        }
    }, 1000);
}
    static final String[] ICON_CHOICE_SHOP = {
            "http://image.lottedfs.com/image/event/info/body_img_10086100060000800007_1.jpg",
            "http://www.manbong.com/mart7/upload/mall/%EB%9F%AD%EC%85%94%EB%A6%AC%EC%9E%A5%EC%8B%9D%20%EB%A7%88%EC%8A%A4%ED%81%AC.jpg",
            "http://dominicanhairsalondaviefl.com/wp-content/uploads/2014/11/una.jpg",
            "http://slodive.com/wp-content/uploads/2013/02/pretty-nail-designs/brown-nail.jpg",
            "http://webneel.com/daily/sites/default/files/images/daily/05-2014/3-face-paint.jpg",
            "http://visual-makeover.com/wp-content/uploads/2011/03/Round-Face-Shape.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg"
    };static final String[] ICON_CHOICE_ARTIST = {
            "http://image.lottedfs.com/image/event/info/body_img_10086100060000800007_1.jpg",
            "http://www.manbong.com/mart7/upload/mall/%EB%9F%AD%EC%85%94%EB%A6%AC%EC%9E%A5%EC%8B%9D%20%EB%A7%88%EC%8A%A4%ED%81%AC.jpg",
            "http://dominicanhairsalondaviefl.com/wp-content/uploads/2014/11/una.jpg",
            "http://slodive.com/wp-content/uploads/2013/02/pretty-nail-designs/brown-nail.jpg",
            "http://webneel.com/daily/sites/default/files/images/daily/05-2014/3-face-paint.jpg",
            "http://visual-makeover.com/wp-content/uploads/2011/03/Round-Face-Shape.jpg",
            "https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg"
    };
    private List<ChoiceData> chInitData() {
        List<ChoiceData> list = new ArrayList<>();
        for (int i=0; i<ICON_CHOICE_SHOP.length; i++) {
            ShopTotalData sd = new ShopTotalData();
            sd.shopName = "NICK NAME"+i;
            sd.shopImage = ICON_CHOICE_SHOP[i];
            sd.shopChoice = "Choice"+i;
            sd.location = "3km";
            list.add(sd);
        }

        for (int i=0; i<ICON_CHOICE_ARTIST.length; i++)
        {
            ArtistTotalData ad = new ArtistTotalData();
            ad.artistName = "NICK NAME"+i;
//            ad.artistImage = ICON_IDS[i];
            ad.artistDiscount="https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg";
            ad.artistImage = ICON_CHOICE_ARTIST[i];
            ad.artistChoice = "Choice"+i;
           *//* ad.location = "3km";
            ad.choiceId="1";*//*
            list.add(ad);

        }
        return list;
    }


//찜내역 읽기 페이지 ----------------------------------------------------------------------------------------------------------------------*/
/*//할인파우치 페이지------------------------------------------------------------------------------------------------------------------------
public void getDiscountData(int id, final OnResultListener<List<DiscountListData>> listener) {
    mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            listener.onSuccess(null,dcInitData());
        }
    }, 1000);
}

    private List<DiscountListData> dcInitData() {
        List<DiscountListData> list = new ArrayList<>();
        for (int i =0; i<5; i++){
            DiscountListData dd = new DiscountListData();
            dd.artistName = "홍길동";
            dd.discountImage = "http://dominicanhairsalondaviefl.com/wp-content/uploads/2014/11/una.jpg";
            dd.shopName = "네일샵";
            dd.discountTime = "16:00~18:00";
            dd.discountPercent = "30%";
            dd.discountYear = "2015";
            dd.discountMonth = "2";
            dd.discountDay = "29";
            list.add(dd);
        }

        return list;
    }

//할인파우치 페이지------------------------------------------------------------------------------------------------------------------------*/


}

