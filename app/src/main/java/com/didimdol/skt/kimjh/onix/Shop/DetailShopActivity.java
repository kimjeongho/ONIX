package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.didimdol.skt.kimjh.onix.Artist.ArtistPagerAdapter;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.DetailShopData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTiemData;
import com.didimdol.skt.kimjh.onix.R;

import java.util.HashMap;

public class DetailShopActivity extends AppCompatActivity {
    private SliderLayout mDemoSlider;
    ShopPagerAdapter photoAdapter;

    RecyclerView recyclerView;
    DetailShopAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    final static int[] ARTISTPROFILEIMAGE = {
            R.drawable.shop_artist_profile_1,
            R.drawable.shop_artist_profile_2
    };

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
        photoAdapter = new ShopPagerAdapter();
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
        }
        // pageslide-----------------------------------------------------------------------------------------

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailShopAdapter();
        recyclerView.setAdapter(mAdapter);

        initData();
    }

    private void initData() {
        DetailShopData sd = new DetailShopData();
        sd.shopTitle = "네일샵";
        for (int i = 0; i<ARTISTPROFILEIMAGE.length; i++){
            ArtistListData ad = new ArtistListData();
            ad.artistChoice = Integer.parseInt(""+100);
            ad.artistContent = "잘하겠습니다";
            ad.artistListImage = ARTISTPROFILEIMAGE[i];
            if( i==0){
                ad.shopName = "/"+sd.shopTitle;
                ad.artistName="홍길동";
                ad.artistSort = "메인 아티스트";}
            else if( i ==1){
                ad.artistName = "김숙자";
                ad.shopName = "/"+sd.shopTitle;
                ad.artistSort = "일반 아티스트";}
            sd.artistListDatas.add(ad);
        }
        ShopTiemData td = new ShopTiemData();
        td.shopWeekDay = "09:00 ~ 21:00";
        td.shopWeekEnd = "09:00 ~ 21:00";
        td.shopWeekEtc = "연중무휴";
        td.shopAddress = "서울시 동작구 상도4동";
        sd.shopTimeDatas.add(td);
        mAdapter.put(sd);

    }
}
