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
import com.didimdol.skt.kimjh.onix.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DetailArtistActivity extends AppCompatActivity {

    private SliderLayout mDemoSlider;
    ArtistPagerAdapter photoAdapter;
    RecyclerView recyclerView;
    DetailArtistAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText inputView;
    List<ArtistCommentData> commentDatas;

    final static String[] NAILTYPE= {"A타입","B타입","C타입","젤네일"};

    final static int[] NAILPRICE = {25000,35000,40000,10000};

    final static String[] COMMENT_ID = {"김정호","김상일","이영석","박유현","최유빈"};

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
        photoAdapter = new ArtistPagerAdapter();
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
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
        }
        // pageslide-----------------------------------------------------------------------------------------
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailArtistAdapter();
        recyclerView.setAdapter(mAdapter);
        inputView = (EditText)findViewById(R.id.edit_message);

        Button btn = (Button)findViewById(R.id.btn_ok);
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


        initData();

    }

    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    private void initData() {
        DetailArtistData da = new DetailArtistData();   //artistprofile
        da.artistImage = R.mipmap.ic_launcher;
        da.artistName = "test";
        da.shopName = "shoptest";
        da.artistContent = "content Test";
        da.nailType = new ArrayList<NailTypeData>();
        for(int i=0; i<NAILTYPE.length ;i++){
            NailTypeData nd = new NailTypeData();   // nail type
            nd.nailPrice = Integer.parseInt(""+NAILPRICE[i]);
            nd.nailType = NAILTYPE[i];
            da.nailType.add(nd);}
        da.artistComment = new ArrayList<ArtistCommentData>();
       for (int i= 0 ; i<5 ; i++){
        ArtistCommentData cd = new ArtistCommentData();     //comment type
        cd.userId = COMMENT_ID[i];
        cd.userComment = "댓글 테스트";
           da.artistComment.add(cd);}
        mAdapter.put(da);

    }
}
