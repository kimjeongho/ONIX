package com.didimdol.skt.kimjh.onix.Shop;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.ShopListData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopListSuccess;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    Spinner searchSpinner;
    public ShopFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }



    ListView listView;
    ShopAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop, container, false);
        listView = (ListView)v.findViewById(R.id.listView);
        mAdapter = new ShopAdapter();
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShopListData mData = (ShopListData) listView.getItemAtPosition(position);
                Toast.makeText(getContext(), "name: " + mData.shopId, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailShopActivity.class);
                intent.putExtra(DetailShopActivity.PARAM_TOTAL_SHOP,mData);
                startActivity(intent);

            }
        });

        searchSpinner = (Spinner)v.findViewById(R.id.spinner_search);
        searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "click: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initData();
        return v;
    }

    private void initData() {
        /*NetworkManager.getInstance().getShopData(4, new NetworkManager.OnResultListener<List<ShopTotalData>>() {
            @Override
            public void onSuccess(Request request, List<ShopTotalData> result) {
                for (ShopTotalData sd : result) {
                    mAdapter.add(sd);
                }
            }

            @Override
            public void onFailure(Request request,int code, Throwable cause) {

            }
        });*/

        NetworkManager.getInstance().getShopTotalDataResult(getContext(), 5, "", "", new NetworkManager.OnResultListener<ShopListSuccess>() {
            @Override
            public void onSuccess(Request request, ShopListSuccess result) {
                mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

}
