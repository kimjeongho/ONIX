package com.didimdol.skt.kimjh.onix.Shop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.didimdol.skt.kimjh.onix.Artist.DetailArtistActivity;
import com.didimdol.skt.kimjh.onix.Board.BoardReadActivity;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceMinusResult;
import com.didimdol.skt.kimjh.onix.DataClass.ChoicePlusResult;
import com.didimdol.skt.kimjh.onix.DataClass.DetailShopData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.OnShopItemClickListener;
import com.didimdol.skt.kimjh.onix.R;

import okhttp3.Request;

public class DetailShopActivity extends AppCompatActivity {
    private SliderLayout mDemoSlider;
    RecyclerView recyclerView;
    DetailShopAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    public static final String PARAM_TOTAL_SHOP = "total";
    public static final int SHOP_TYPE = 3;
//    public static final String PARAM_DETAIL_SHOP = "detailshop";
    ShopTotalData shopTotalData;
    int putData;
    int data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shop);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView onixHome = (ImageView) findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // pageslide-----------------------------------------------------------------------------------------
       /*
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.set("Hannibal", R.drawable.onix_shop_main_list_data_1);
        file_maps.set("Big Bang Theory", R.drawable.onix_shop_main_list_data_2);
        file_maps.set("House of Cards", R.drawable.onix_shop_main_list_data_3);
        file_maps.set("Game of Thrones", R.drawable.onix_shop_main_list_data_4);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView.image(file_maps.get(name));

            mDemoSlider.addSlider(textSliderView);
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        }*/
        // pageslide-----------------------------------------------------------------------------------------


        //serializable------------------------------------------------------------------------------------------
        Intent intent = getIntent();
//        ShopTotalData data =  (ShopTotalData)intent.getSerializableExtra(PARAM_TOTAL_SHOP);
        data = intent.getIntExtra(PARAM_TOTAL_SHOP, 0);
        //serializable------------------------------------------------------------------------------------------

        //DetailArtistActivity------------------------------------------------------------------------------------------
        /*Intent putIntent = getIntent();
        putData = putIntent.getIntExtra(PARAM_DETAIL_SHOP, 0);*/

        //DetailArtistActivity------------------------------------------------------------------------------------------
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailShopAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnShopItemClickListener(new OnShopItemClickListener() {
            @Override
            public void onCallClick(View view, ShopTotalData shopTotalData) {
                Toast.makeText(DetailShopActivity.this, "callclick", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+shopTotalData.callNumber+""));
                if (ActivityCompat.checkSelfPermission(DetailShopActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }

                startActivity(intent);
            }

            @Override
            public void onChoiceClick(View view, ShopTotalData shopTotalData) {
                Toast.makeText(DetailShopActivity.this,"choiceclick",Toast.LENGTH_SHORT).show();
                if (shopTotalData.choiceSort == 0) {      //??
                    // post
                    postInitData();
                } else {
                    // delete
                    deleteInitData();
                }

            }

            @Override
            public void onArtistListClick(View view, ShopArtistListData shopArtistListData) {
                Toast.makeText(DetailShopActivity.this,"choiceclick"+shopArtistListData.artistId,Toast.LENGTH_SHORT).show();
                Intent artistIntent = new Intent(DetailShopActivity.this, DetailArtistActivity.class);
                artistIntent.putExtra(DetailArtistActivity.PARAM_TOTAL_ARTIST,shopArtistListData.artistId);
                startActivity(artistIntent);
            }

            @Override
            public void onShopMapClick(View view, ShopTotalData shopTotalData) {
                Intent mapIntent = new Intent(DetailShopActivity.this, ShopMapActivity.class);
//                mapIntent.putExtra(ShopMapActivity.PARAM_LATITUDE,shopTotalData.latitude);
//                mapIntent.putExtra(ShopMapActivity.PARAM_LONGITUDE,shopTotalData.longitude);
                mapIntent.putExtra(ShopMapActivity.PARAM_LOCATION,shopTotalData);
                startActivity(mapIntent);
            }


        });
//        mAdapter.set(data);
                initData();
    }

    private void postInitData() {
        NetworkManager.getInstance().setChoicePlusResult(this, data, 3, new NetworkManager.OnResultListener<ChoicePlusResult>() {
            //context artistId, targetId
            @Override
            public void onSuccess(Request request, ChoicePlusResult result) {
                if (result.failResult == null) {
                    Toast.makeText(DetailShopActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                    initData();
                }

            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

    private void deleteInitData() {
        NetworkManager.getInstance().setChoiceMinusResult(this, data, SHOP_TYPE, new NetworkManager.OnResultListener<ChoiceMinusResult>() {
            @Override
            public void onSuccess(Request request, ChoiceMinusResult result) {
                if (result.failResult == null) {
                    Toast.makeText(DetailShopActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();

                    initData();
                }

            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

    private void initData() {
       /* NetworkManager.getInstance().getShopDetailData(1, new NetworkManager.OnResultListener<DetailShopData>() {
            @Override
            public void onSuccess(Request request, DetailShopData result) {
                mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request,int code, Throwable cause) {

            }
        });*/

        NetworkManager.getInstance().getShopDetailDataResult(this, data, new NetworkManager.OnResultListener<ShopTotalData>() {
            @Override
            public void onSuccess(Request request, ShopTotalData result) {
                mAdapter.put(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }
}
