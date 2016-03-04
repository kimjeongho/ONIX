package com.didimdol.skt.kimjh.onix.Artist;

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
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentData;
import com.didimdol.skt.kimjh.onix.DataClass.DetailArtistData;
import com.didimdol.skt.kimjh.onix.DataClass.NailTypeData;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.OnArtistItemClickListener;
import com.didimdol.skt.kimjh.onix.R;
import com.didimdol.skt.kimjh.onix.Shop.DetailShopActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DetailArtistActivity extends AppCompatActivity {

    private SliderLayout mDemoSlider;
//    ArtistPagerAdapter photoAdapter;
    RecyclerView recyclerView;
    DetailArtistAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText inputView;
    List<ArtistCommentData> commentDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView onixHome = (ImageView)findViewById(R.id.onix_home);
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
        file_maps.put("Hannibal", R.drawable.dummy_1);
        file_maps.put("Big Bang Theory", R.drawable.dummy_2);
        file_maps.put("House of Cards", R.drawable.dummy_3);
        file_maps.put("Game of Thrones", R.drawable.dummy_4);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView.image(file_maps.get(name));

            mDemoSlider.addSlider(textSliderView);
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        }*/
        // pageslide-----------------------------------------------------------------------------------------
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailArtistAdapter();
        recyclerView.setAdapter(mAdapter);
        inputView = (EditText)findViewById(R.id.edit_message);

        Button btn = (Button) findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputView.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    commentDatas = new ArrayList<ArtistCommentData>();
                    commentDatas.add(new ArtistCommentData("홍길동", text));
                    mAdapter.addAll(commentDatas);  // commentDatas를 메소드 인자로 보낸다.
                    inputView.setText("");
                    recyclerView.smoothScrollToPosition(mAdapter.getItemCount()-1);
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnArtistItemClickListener() {
            @Override
            public void onShopClick(View view, int position) {
                Toast.makeText(DetailArtistActivity.this,"shopclick",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChoiceClick(View view, int position) {
                Toast.makeText(DetailArtistActivity.this,"choiceclick",Toast.LENGTH_SHORT).show();
            }
        });

        initData();
    }

    private void initData() {
        NetworkManager.getInstance().getArtistDetailData(1, new NetworkManager.OnResultListener<DetailArtistData>() {
            @Override
            public void onSuccess(DetailArtistData result) {
                mAdapter.put(result);
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }


}
