package com.didimdol.skt.kimjh.onix.Menu.MenuChoice;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.didimdol.skt.kimjh.onix.Artist.ArtistView;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.Shop.ShopView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-28.
 */
public class ChoiceAdapter extends BaseAdapter {
    List<ChoiceData> items = new ArrayList<ChoiceData>();
    private static final int VIEW_TYPE_COUNT = 2;

    private static final int TYPE_ARTIST = 0;
    private static final int TYPE_SHOP = 1;

    public void add(ChoiceData data){
        items.add(data);
        notifyDataSetChanged();
    }


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
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        ChoiceData data = items.get(position);
        if(data instanceof ArtistTotalData){
            return TYPE_ARTIST;
        } else if(data instanceof ShopTotalData) {
            return TYPE_SHOP;
        }
        else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)){
            case TYPE_ARTIST:{
                ArtistView view;
                if(convertView != null && convertView instanceof ArtistView){
                    view = (ArtistView)convertView;
                } else {
                    view = new ArtistView(parent.getContext());
                }
                view.setArtistData((ArtistTotalData)items.get(position));
                return view;
            }
            case TYPE_SHOP:
                default: {
                    ShopView view;
                    if (convertView != null && convertView instanceof ShopView) {
                        view = (ShopView) convertView;
                    } else {
                        view = new ShopView(parent.getContext());
                    }
                    view.setShopData((ShopTotalData) items.get(position));
                    return view;
                }
            }
        }
}
