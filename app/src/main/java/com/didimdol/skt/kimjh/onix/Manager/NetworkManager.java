package com.didimdol.skt.kimjh.onix.Manager;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentData;
import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceData;
import com.didimdol.skt.kimjh.onix.DataClass.DetailArtistData;
import com.didimdol.skt.kimjh.onix.DataClass.DetailShopData;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountData;
import com.didimdol.skt.kimjh.onix.DataClass.NailTypeData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopLocationData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTiemData;
import com.didimdol.skt.kimjh.onix.MyApplication;
import com.didimdol.skt.kimjh.onix.PersistentCookieStore;
import com.didimdol.skt.kimjh.onix.R;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

        mClient = builder.build();
    }

    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);
        public void onFailure(Request request, int code, Throwable cause);
    }

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

    Handler mHandler = new Handler(Looper.getMainLooper());

    static class CallbackObject<T>{
        Request request;
        T result;
        IOException exception;
        OnResultListener<T> listener;
    }

    private static final String URL_FORMAT = "http://";
    public Request getArtistDetailData(Context context, int id, final OnResultListener<DetailArtistData> listener){
        String url = String.format(URL_FORMAT, URLEncoder.encode("utf-8"),id);

        final CallbackObject<DetailArtistData> callbackObject = new CallbackObject<DetailArtistData>();

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

            }
        });


        return request;
    }


    //아티스트 상세 페이지-----------------------------------------------------------------------------------------------------------------
    public void getArtistDetailData(int id, final OnResultListener<DetailArtistData> listener) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(null,daInitData());
            }
        }, 1000);
    }

    final static String[] NAILTYPE = {"A타입", "B타입", "C타입", "젤네일"};

    final static int[] NAILPRICE = {25000, 35000, 40000, 10000};

    private DetailArtistData daInitData() {
        DetailArtistData da = new DetailArtistData();   //artistprofile
        da.artistPhotos = "https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg";
        da.artistImage = "https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg";
        da.artistName = "test";
        da.shopName = "shoptest";
        da.artistContent = "content Test";
        da.nailType = new ArrayList<NailTypeData>();
        for (int i = 0; i < NAILTYPE.length; i++) {
            NailTypeData nd = new NailTypeData();   // nail type
            nd.nailPrice = Integer.parseInt("" + NAILPRICE[i]);
            nd.nailType = NAILTYPE[i];
            da.nailType.add(nd);
        }
        da.artistComment = new ArrayList<ArtistCommentData>();
        ArtistCommentData cd = new ArtistCommentData();     //comment type
        cd.userId = "가나다";
        cd.userComment = "댓글 테스트";
        da.artistComment.add(cd);
        return da;
    }

    //아티스트 상세 페이지-----------------------------------------------------------------------------------------------------------------

    //샵 상세 페이지 ----------------------------------------------------------------------------------------------------------------------
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

    private DetailShopData dsInitData() {
        DetailShopData sd = new DetailShopData();
        sd.shopPhotos = "https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg";
        sd.shopTitle = "네일샵";
        for (int i = 0; i < ARTISTPROFILEIMAGE.length; i++) {
            ArtistListData ad = new ArtistListData();
            ad.artistChoice = Integer.parseInt("" + 100);
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
        sd.shopTimeDatas = new ShopTiemData();
        /*td.shopWeekDay = "09:00 ~ 21:00";
        td.shopWeekEnd = "09:00 ~ 21:00";
        td.shopWeekEtc = "연중무휴";
        td.shopAddress = "서울시 동작구 상도4동";*/
        sd.shopTimeDatas.shopWeekDay = "09:00 ~ 21:00";
        sd.shopTimeDatas.shopWeekEnd = "09:00 ~ 21:00";
        sd.shopTimeDatas.shopWeekEtc = "연중무휴";
        sd.shopTimeDatas.shopAddress = "서울시 동작구 상도4동";
        sd.shopLocation = new ShopLocationData();
        /*sd.shopLocation.shopLatitude = 123;
        sd.shopLocation.shopLongitude = 123;*/
        return sd;

    }

    //샵 상세 페이지 ----------------------------------------------------------------------------------------------------------------------
//아티스트 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
    public void getArtistData(int page, final OnResultListener<List<ArtistData>> listener) {
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

    private List<ArtistData> atInitData() {
        List<ArtistData> list = new ArrayList<>();
        for (int i = 0; i < ICON_IDS.length; i++) {
            ArtistData ad = new ArtistData();
            ad.artistName = "NICK NAME" + i;
            ad.artistDiscount = "http://image.lottedfs.com/image/event/info/body_img_10086100060000800007_1.jpg";
            ad.artistImage = ICON_IDS[i];
            ad.artistChoice = "Choice" + i;
            ad.location = "3km";
            list.add(ad);
        }
        return list;
    }

    //아티스트 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
//샵 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
    public void getShopData(int page, final OnResultListener<List<ShopData>> listener) {
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

    private List<ShopData> shInitData() {
        List<ShopData> list = new ArrayList<>();
        for (int i = 0; i < ICON_IDS_SHOP.length; i++) {
            ShopData sd = new ShopData();
            sd.shopName = "NICK NAME" + i;
            sd.shopImage = ICON_IDS_SHOP[i];
            sd.shopChoice = "Choice" + i;
            sd.location = "3km";
            list.add(sd);
        }
        return list;
    }

    //샵 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
//게시판 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
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
            bd.boardName = "NICKNAME"+i;
            bd.boardCategory = "QnA";
            bd.boardTime = "02월 24일";
            bd.boardTitle = "TITLE" +i;
            bd.boardContent = CONTENTS[i];
            list.add(bd);

        }
        return list;
    }
//게시판 리스트 페이지 ----------------------------------------------------------------------------------------------------------------------
//게시판 읽기 페이지 ----------------------------------------------------------------------------------------------------------------------
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
        br.boardName = "홍길동";
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

//게시판 읽기 페이지 ----------------------------------------------------------------------------------------------------------------------


//찜내역 읽기 페이지 ----------------------------------------------------------------------------------------------------------------------
public void getChoiceData(int id, final OnResultListener<List<ChoiceData>> listener) {
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
            ShopData sd = new ShopData();
            sd.shopName = "NICK NAME"+i;
            sd.shopImage = ICON_CHOICE_SHOP[i];
            sd.shopChoice = "Choice"+i;
            sd.location = "3km";
            list.add(sd);
        }

        for (int i=0; i<ICON_CHOICE_ARTIST.length; i++)
        {
            ArtistData ad = new ArtistData();
            ad.artistName = "NICK NAME"+i;
//            ad.artistImage = ICON_IDS[i];
            ad.artistDiscount="https://s-media-cache-ak0.pinimg.com/236x/43/4b/30/434b30ea9695f44a2ddf4772d5f1bf9c.jpg";
            ad.artistImage = ICON_CHOICE_ARTIST[i];
            ad.artistChoice = "Choice"+i;
            ad.location = "3km";
            ad.choiceId="1";
            list.add(ad);

        }
        return list;
    }


//찜내역 읽기 페이지 ----------------------------------------------------------------------------------------------------------------------
//할인파우치 페이지------------------------------------------------------------------------------------------------------------------------
public void getDiscountData(int id, final OnResultListener<List<DiscountData>> listener) {
    mHandler.postDelayed(new Runnable() {
        @Override
        public void run() {
            listener.onSuccess(null,dcInitData());
        }
    }, 1000);
}

    private List<DiscountData> dcInitData() {
        List<DiscountData> list = new ArrayList<>();
        for (int i =0; i<5; i++){
            DiscountData dd = new DiscountData();
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

//할인파우치 페이지------------------------------------------------------------------------------------------------------------------------


}

