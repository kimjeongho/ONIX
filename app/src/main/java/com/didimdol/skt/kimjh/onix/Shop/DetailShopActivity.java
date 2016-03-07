package com.didimdol.skt.kimjh.onix.Shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.DetailShopData;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shop);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView onixHome = (ImageView)findViewById(R.id.onix_home);
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
        file_maps.put("Hannibal", R.drawable.onix_shop_main_list_data_1);
        file_maps.put("Big Bang Theory", R.drawable.onix_shop_main_list_data_2);
        file_maps.put("House of Cards", R.drawable.onix_shop_main_list_data_3);
        file_maps.put("Game of Thrones", R.drawable.onix_shop_main_list_data_4);

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
        ShopTotalData data =  (ShopTotalData)intent.getSerializableExtra(PARAM_TOTAL_SHOP);

        //serializable------------------------------------------------------------------------------------------
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailShopAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnShopItemClickListener(new OnShopItemClickListener() {
            @Override
            public void onCallClick(View view, int position) {
                Toast.makeText(DetailShopActivity.this,"callclick",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChoiceClick(View view, int position) {
                Toast.makeText(DetailShopActivity.this,"choiceclick",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onArtistListClick(View view, int position) {
                Toast.makeText(DetailShopActivity.this,"artistlistclick",Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.put(data);
                initData();
    }

    private void initData() {
       /* NetworkManager.getInstance().getShopDetailData(1, new NetworkManager.OnResultListener<DetailShopData>() {
            @Override
            public void onSuccess(Request request, DetailShopData result) {
                mAdapter.put(result);
            }

            @Override
            public void onFailure(Request request,int code, Throwable cause) {

            }
        });*/
    }
}
