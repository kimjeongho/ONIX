package com.didimdol.skt.kimjh.onix.Shop;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopData;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {


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
                ShopData mData = (ShopData) listView.getItemAtPosition(position);
                Toast.makeText(getContext(), "name: " + mData.shopName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailShopActivity.class);
                startActivity(intent);

            }
        });

        initData();
        return v;
    }

    private void initData() {
        NetworkManager.getInstance().getShopData(4, new NetworkManager.OnResultListener<List<ShopData>>() {
            @Override
            public void onSuccess(List<ShopData> result) {
                for (ShopData sd : result) {
                    mAdapter.add(sd);
                }
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }

}
