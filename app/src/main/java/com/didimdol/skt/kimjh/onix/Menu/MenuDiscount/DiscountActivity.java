package com.didimdol.skt.kimjh.onix.Menu.MenuDiscount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.didimdol.skt.kimjh.onix.Artist.DetailArtistActivity;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountData;
import com.didimdol.skt.kimjh.onix.MainActivity;
import com.didimdol.skt.kimjh.onix.R;

public class DiscountActivity extends AppCompatActivity {

    ListView listView;
    DiscountAdapter mAdapter;
    static final int ICON_IDS =
            R.drawable.sale_pouch_artist_data_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        listView = (ListView)findViewById(R.id.list_discount);
        mAdapter = new DiscountAdapter();
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DiscountActivity.this, DetailArtistActivity.class);
                startActivity(intent);
            }
        });

        initData();
    }

    private void initData() {
        for (int i =0; i<5; i++){
            DiscountData dd = new DiscountData();
            dd.iconid = ICON_IDS;
            dd.artistName = "홍길동";
            dd.shopName = "네일샵";
            dd.discountTime = "16:00~18:00";
            dd.discountPercent = "30%";
            dd.discountYear = "2015";
            dd.discountMonth = "2";
            dd.discountDay = "29";
            mAdapter.add(dd);
        }
    }
}
