package com.didimdol.skt.kimjh.onix.Shop;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.didimdol.skt.kimjh.onix.DataClass.ShopListData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopListSuccess;

import java.util.ArrayList;

/**
 * Created by kimjh on 2016-02-23.
 */
public class ShopAdapter extends BaseAdapter {
    ArrayList<ShopListData> items = new ArrayList<ShopListData>();

    public ShopAdapter(){
    }

    public void set(ShopListSuccess data){
        items.addAll(data.shopList);
        notifyDataSetChanged();
    }

   /* public void add(ShopTotalData data){
        items.add(data);
        notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShopView view;
        if(convertView == null){
            view = new ShopView(parent.getContext());
        } else {
            view = (ShopView)convertView;
        }
        view.setShopData(items.get(position));
        return view;

    }
}
