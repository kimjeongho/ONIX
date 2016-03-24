package com.didimdol.skt.kimjh.onix.Menu.MenuDiscount;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.didimdol.skt.kimjh.onix.Artist.DetailArtistActivity;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountListData;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountListSuccess;
import com.didimdol.skt.kimjh.onix.MainActivity;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import java.util.List;

import okhttp3.Request;

public class DiscountActivity extends AppCompatActivity {

    ListView listView;
    DiscountAdapter mAdapter;
    int page =1;
    boolean isLast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        ImageView onixHome = (ImageView) findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(DiscountActivity.this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();
            }
        });

        listView = (ListView)findViewById(R.id.list_discount);
        mAdapter = new DiscountAdapter();
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiscountListData mData = (DiscountListData) listView.getItemAtPosition(position);
                Intent intent = new Intent(DiscountActivity.this, DetailArtistActivity.class);
                intent.putExtra(DetailArtistActivity.PARAM_TOTAL_ARTIST, mData.artistId);
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLast && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int itemCount = mAdapter.getCount();
                    int page = itemCount / 10;
                    page = (itemCount % 10 > 0) ? page + 1 : page;
                    getMoreItem(page);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
                    isLast = true;
                } else {
                    isLast = false;
                }
            }
        });


        initData();
    }
    boolean isMoreData = false;
    ProgressDialog dialog = null;
    //리스트뷰 확장
    private void getMoreItem(int page) {
        if (isMoreData) return;
        isMoreData = true;
        NetworkManager.getInstance().getDiscountListDataResult(this ,page, new NetworkManager.OnResultListener<DiscountListSuccess>() {
            @Override
            public void onSuccess(Request request, DiscountListSuccess result) {
                mAdapter.set(result);
                isMoreData = false;
                dialog.dismiss();
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                isMoreData = false;
                dialog.dismiss();
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading........");
        dialog.show();
    }

    private void initData() {
        NetworkManager.getInstance().getDiscountListDataResult(this ,page, new NetworkManager.OnResultListener<DiscountListSuccess>() {
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
