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

import com.didimdol.skt.kimjh.onix.DataClass.ShopData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {


    public ShopFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    ListView listView;
    ShopAdapter shopAdapter;
    static final int[] ICON_IDS_SHOP ={
            R.drawable.onix_shop_main_list_data_1,
            R.drawable.onix_shop_main_list_data_2,
            R.drawable.onix_shop_main_list_data_3,
            R.drawable.onix_shop_main_list_data_4
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop, container, false);
        listView = (ListView)v.findViewById(R.id.listView);
        shopAdapter = new ShopAdapter();
        listView.setAdapter(shopAdapter);
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
        for (int i=0; i< ICON_IDS_SHOP.length; i++)
        {
            ShopData sd = new ShopData();
            sd.shopName = "NICK NAME"+i;
            sd.iconid = ICON_IDS_SHOP[i];
            sd.shopChoice = "Choice"+i;
            sd.location = "3km";
            shopAdapter.add(sd);
        }
    }

}
