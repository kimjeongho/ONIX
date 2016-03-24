package com.didimdol.skt.kimjh.onix.Artist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentResult;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceMinusResult;
import com.didimdol.skt.kimjh.onix.DataClass.ChoicePlusResult;
import com.didimdol.skt.kimjh.onix.LoginDialogFragment;
import com.didimdol.skt.kimjh.onix.MainActivity;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.OnArtistItemClickListener;
import com.didimdol.skt.kimjh.onix.R;
import com.didimdol.skt.kimjh.onix.Shop.DetailShopActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;


public class DetailArtistActivity extends AppCompatActivity {

    private SliderLayout mDemoSlider;
    //    ArtistPagerAdapter photoAdapter;
    RecyclerView recyclerView;
    DetailArtistAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText inputView;
    List<ArtistCommentData> commentDatas;
    int putData;
    int data;



    public static final String PARAM_TOTAL_ARTIST = "total";
//    public static final String PARAM_DETAIL_ARITST = "detailArtist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView onixHome = (ImageView) findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(DetailArtistActivity.this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();
            }
        });

        //artistid 값 가져옴
        Intent intent = getIntent();
        data = intent.getIntExtra(PARAM_TOTAL_ARTIST, 0);
        //serializable------------------------------------------------------------------------------------------
//        ArtistTotalData data =  (ArtistTotalData)intent.getSerializableExtra(PARAM_TOTAL_ARTIST);
        //serializable------------------------------------------------------------------------------------------

        //shopPageExtra-----------------------------------------------------------------------------------------
        /*Intent putIntent = getIntent();
        putData = putIntent.getIntExtra(PARAM_DETAIL_ARITST, 0);*/

        //shopPageExtra-----------------------------------------------------------------------------------------


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailArtistAdapter();
        recyclerView.setAdapter(mAdapter);
        inputView = (EditText) findViewById(R.id.edit_message);

        //댓글 등록
        Button btn = (Button) findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentInitData();  // network
            }
        });

        mAdapter.setOnItemClickListener(new OnArtistItemClickListener() {
            //샵 바로가기 클릭
            @Override
            public void onShopClick(View view, ArtistTotalData artistTotalData) {
                Intent shopIntent = new Intent(DetailArtistActivity.this, DetailShopActivity.class);
                shopIntent.putExtra(DetailShopActivity.PARAM_TOTAL_SHOP, artistTotalData.shopId);
                startActivity(shopIntent);
            }

            //찜하기 클릭
            @Override
            public void onChoiceClick(View view, ArtistTotalData artistTotalData) {
                if (artistTotalData.choiceSort == 0) {      //찜하기를 눌렀으면
                    // post
                    postInitData();
                } else {
                    // delete
                    deleteInitData();
                }

            }
        });

//        mAdapter.set(data);
        initData();
    }

    private void commentInitData() {    //댓글 작성
        String commentText= inputView.getText().toString();
        if (!TextUtils.isEmpty(commentText)) {
            NetworkManager.getInstance().setArtistCommentResult(this, data, commentText, new NetworkManager.OnResultListener<ArtistCommentResult>() {
                @Override
                public void onSuccess(Request request, ArtistCommentResult result) {
                    if (result.failResult == null) {
                        Toast.makeText(DetailArtistActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                        /*if (!TextUtils.isEmpty(DetailArtistActivity.this.commentText)) {
                            inputView.setText("");
                        }*/
                        inputView.setText("");
                        initData();
                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {
                    Toast.makeText(DetailArtistActivity.this, "fail ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void postInitData() {
        NetworkManager.getInstance().setChoicePlusResult(this, data, 2, new NetworkManager.OnResultListener<ChoicePlusResult>() {
            //context artistId, targetId
            @Override
            public void onSuccess(Request request, ChoicePlusResult result) {
                if (result.failResult == null) {
//                    Toast.makeText(DetailArtistActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                    initData();
                } else {
                   /* LoginDialogFragment f = new LoginDialogFragment();
                    f.show(getSupportFragmentManager(),"dialog");*/
                }

            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                LoginDialogFragment f = new LoginDialogFragment();
                f.show(getSupportFragmentManager(),"dialog");
            }
        });
    }

    private void deleteInitData() {
        NetworkManager.getInstance().setChoiceMinusResult(this, data, 2, new NetworkManager.OnResultListener<ChoiceMinusResult>() {
            @Override
            public void onSuccess(Request request, ChoiceMinusResult result) {
                if (result.failResult == null) {
                    Toast.makeText(DetailArtistActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();

                    initData();
                }

            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

    private void initData() {

        NetworkManager.getInstance().getArtistDetailDataResult(this, data, new NetworkManager.OnResultListener<ArtistTotalData>() {
            @Override
            public void onSuccess(Request request, ArtistTotalData result) {
                mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

        /* NetworkManager.getInstance().getArtistDetailDataResult(1, new NetworkManager.OnResultListener<ArtistTotalData>() {
            @Override
            public void onSuccess(ArtistTotalData result) {
                mAdapter.set(result);
            }

            @Override
            public void onFailure(int code) {

            }
        });*/

    }

}
