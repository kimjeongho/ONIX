package com.didimdol.skt.kimjh.onix.Menu.MenuChoice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.Artist.ArtistListView;
import com.didimdol.skt.kimjh.onix.Artist.DetailArtistActivity;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceData;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.ShopListData;
import com.didimdol.skt.kimjh.onix.MainActivity;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.OnChoiceItemClickListener;
import com.didimdol.skt.kimjh.onix.R;
import com.didimdol.skt.kimjh.onix.Shop.DetailShopActivity;

import java.util.List;

import okhttp3.Request;

import static android.widget.Toast.LENGTH_SHORT;

public class ChoiceActivity extends AppCompatActivity {
    ChoiceAdapter mAdapter;
    ListView listView;
    ArtistListView artistListView;
    ArtistListData data;
    int page = 1;
    boolean isLast = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        ImageView onixHome = (ImageView) findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(ChoiceActivity.this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();
            }
        });

        listView = (ListView)findViewById(R.id.listView_choice);
        mAdapter = new ChoiceAdapter();
      /*  mAdapter.setOnChoiceClickListener(new OnChoiceItemClickListener() {
            @Override
            public void OnArtistListClick(View v, ArtistListData artistListData) {
                Toast.makeText(ChoiceActivity.this,""+artistListData.artistId,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnShopListClick(View v, ShopListData shopListData) {
                Toast.makeText(ChoiceActivity.this,""+shopListData.shopId,Toast.LENGTH_SHORT).show();
            }
        });*/

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ArtistListData artistListData = (ArtistListData) listView.getItemAtPosition(position);
//                ShopListData shopListData = (ShopListData)listView.getItemAtPosition(position);
               /* Toast.makeText(ChoiceActivity.this,""+position,Toast.LENGTH_SHORT).show();
                int type = position;*/
                ChoiceData data = mAdapter.items.get(position);
                if (data instanceof ArtistListData) {
                    ArtistListData mData = (ArtistListData) listView.getItemAtPosition(position);
                    Intent intent = new Intent(ChoiceActivity.this, DetailArtistActivity.class);
                    intent.putExtra(DetailArtistActivity.PARAM_TOTAL_ARTIST, mData.artistId);
                    startActivity(intent);
                } else if (data instanceof ShopListData) {
                    ShopListData mData = (ShopListData) listView.getItemAtPosition(position);
                    Toast.makeText(ChoiceActivity.this, "name: " + mData.shopId, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChoiceActivity.this, DetailShopActivity.class);
                    intent.putExtra(DetailShopActivity.PARAM_TOTAL_SHOP, mData.shopId);
                    startActivity(intent);
                }


            }
        });
        listView.setAdapter(mAdapter);

//        initData();
    }

    //리스트뷰 확장
    boolean isMoreData = false;
    ProgressDialog dialog = null;
    private void getMoreItem(int page) {
        if (isMoreData) return;
        isMoreData = true;

        NetworkManager.getInstance().getChoiceDataResult(this, page, new NetworkManager.OnResultListener<ChoiceSuccess>() {
            @Override
            public void onSuccess(Request request, ChoiceSuccess result) {
                mAdapter.clear();
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        /*NetworkManager.getInstance().getChoiceData(4, new NetworkManager.OnResultListener<List<ChoiceData>>() {
            @Override
            public void onSuccess(Request request, List<ChoiceData> result) {
                for (ChoiceData cd : result) {
                    mAdapter.add(cd);
                }
            }

            @Override
            public void onFailure(Request request,int code, Throwable cause) {

            }
        });*/
        NetworkManager.getInstance().getChoiceDataResult(this, page, new NetworkManager.OnResultListener<ChoiceSuccess>() {
            @Override
            public void onSuccess(Request request, ChoiceSuccess result) {
                mAdapter.clear();
                mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

    }

}
