package com.didimdol.skt.kimjh.onix.Menu.MenuDiscount;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.didimdol.skt.kimjh.onix.Artist.DetailArtistActivity;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountListData;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountListSuccess;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import java.util.List;

import okhttp3.Request;

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
                DiscountListData mData = (DiscountListData)listView.getItemAtPosition(position);
                Intent intent = new Intent(DiscountActivity.this, DetailArtistActivity.class);
                intent.putExtra(DetailArtistActivity.PARAM_TOTAL_ARTIST,mData.artistId);
                startActivity(intent);
            }
        });

        initData();
    }

    private void initData() {
        NetworkManager.getInstance().getDiscountListDataResult(this ,1, new NetworkManager.OnResultListener<DiscountListSuccess>() {
            @Override
            public void onSuccess(Request request, DiscountListSuccess result) {

                    mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }
}
