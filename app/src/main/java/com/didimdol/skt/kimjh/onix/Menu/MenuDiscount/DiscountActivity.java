package com.didimdol.skt.kimjh.onix.Menu.MenuDiscount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.didimdol.skt.kimjh.onix.Artist.DetailArtistActivity;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceData;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountData;
import com.didimdol.skt.kimjh.onix.MainActivity;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import java.util.List;

public class DiscountActivity extends AppCompatActivity {

    ListView listView;
    DiscountAdapter mAdapter;


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
        NetworkManager.getInstance().getDiscountData(4, new NetworkManager.OnResultListener<List<DiscountData>>() {
            @Override
            public void onSuccess(List<DiscountData> result) {
                for (DiscountData dd : result) {
                    mAdapter.add(dd);
                }
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }
}
