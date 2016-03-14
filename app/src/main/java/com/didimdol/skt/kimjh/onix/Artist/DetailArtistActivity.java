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
    String commentText;


    public static final String PARAM_TOTAL_ARTIST = "total";
    public static final String PARAM_DETAIL_ARITST = "detailArtist";
    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //serializable------------------------------------------------------------------------------------------
        Intent intent = getIntent();
//        ArtistTotalData data =  (ArtistTotalData)intent.getSerializableExtra(PARAM_TOTAL_ARTIST);
        data = intent.getIntExtra(PARAM_TOTAL_ARTIST, 0);
        //serializable------------------------------------------------------------------------------------------

        //shopPageExtra-----------------------------------------------------------------------------------------
        Intent putIntent = getIntent();
        putData = putIntent.getIntExtra(PARAM_DETAIL_ARITST, 0);

        //shopPageExtra-----------------------------------------------------------------------------------------

        ImageView onixHome = (ImageView) findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        pager.setPageMargin(10);
// pageslide-----------------------------------------------------------------------------------------
//        photoAdapter = new ArtistPagerAdapter();
       /* mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.set("Hannibal", R.drawable.dummy_1);
        file_maps.set("Big Bang Theory", R.drawable.dummy_2);
        file_maps.set("House of Cards", R.drawable.dummy_3);
        file_maps.set("Game of Thrones", R.drawable.dummy_4);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView.image(file_maps.get(name));

            mDemoSlider.addSlider(textSliderView);
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        }*/
        // pageslide-----------------------------------------------------------------------------------------
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailArtistAdapter();
        recyclerView.setAdapter(mAdapter);
        inputView = (EditText) findViewById(R.id.edit_message);

        Button btn = (Button) findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (!TextUtils.isEmpty(commentText)) {
                    commentDatas = new ArrayList<ArtistCommentData>();
                    commentDatas.add(new ArtistCommentData("홍길동", commentText, ""));
                    mAdapter.addAll(commentDatas);  // commentDatas를 메소드 인자로 보낸다.
                    inputView.setText("");
                    recyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                }*/
                commentInitData();  // network

            }
        });

        mAdapter.setOnItemClickListener(new OnArtistItemClickListener() {
            @Override
            public void onShopClick(View view, ArtistTotalData artistTotalData) {
                Toast.makeText(DetailArtistActivity.this, "shopclick" + artistTotalData.shopId, Toast.LENGTH_SHORT).show();
                Intent shopIntent = new Intent(DetailArtistActivity.this, DetailShopActivity.class);
                shopIntent.putExtra(DetailShopActivity.PARAM_DETAIL_SHOP, artistTotalData.shopId);
                startActivity(shopIntent);
            }

            @Override
            public void onChoiceClick(View view, ArtistTotalData artistTotalData) {
//                if (check == false) {
//                    Toast.makeText(DetailArtistActivity.this, "choiceclick" + artistTotalData.artistId, Toast.LENGTH_SHORT).show();
//                    check = true;
//                } else {
//                    Toast.makeText(DetailArtistActivity.this, "choicecancelclick", Toast.LENGTH_SHORT).show();
//                    check = false;
//                }
                if (artistTotalData.choiceSort == 0) {      //??
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

    private void commentInitData() {
        commentText = inputView.getText().toString();
        if (!TextUtils.isEmpty(commentText)) {
            NetworkManager.getInstance().setArtistCommentResult(this, data, commentText, new NetworkManager.OnResultListener<ArtistCommentResult>() {
                @Override
                public void onSuccess(Request request, ArtistCommentResult result) {
                    if (result.failResult == null) {
                        Toast.makeText(DetailArtistActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                        if (!TextUtils.isEmpty(commentText)) {
                            inputView.setText("");
                        }
                        initData();
                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

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
                    Toast.makeText(DetailArtistActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                    initData();
                }

            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

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
