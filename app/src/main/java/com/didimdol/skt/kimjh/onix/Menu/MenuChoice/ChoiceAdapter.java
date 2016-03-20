package com.didimdol.skt.kimjh.onix.Menu.MenuChoice;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.didimdol.skt.kimjh.onix.Artist.ArtistListView;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceData;
import com.didimdol.skt.kimjh.onix.DataClass.ChoiceSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.ShopListData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.OnChoiceItemClickListener;
import com.didimdol.skt.kimjh.onix.Shop.ShopView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-28.
 */
public class ChoiceAdapter extends BaseAdapter implements OnChoiceItemClickListener{
    List<ChoiceData> items = new ArrayList<ChoiceData>();
    private static final int VIEW_TYPE_COUNT = 2;

    private static final int TYPE_ARTIST = 0;
    private static final int TYPE_SHOP = 1;

    /*public void add(ChoiceData data){
        items.add(data);
        notifyDataSetChanged();
    }*/

    public void set(ChoiceSuccess data){
        items.addAll(data.artistsList);
        items.addAll(data.shopsList);
        notifyDataSetChanged();
    }

    public void clear(ChoiceSuccess data){
        items.clear();
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
        if(data instanceof ArtistListData){
            return TYPE_ARTIST;
        } else if(data instanceof ShopListData) {
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
                ArtistListView view;
                if(convertView != null && convertView instanceof ArtistListView){
                    view = (ArtistListView)convertView;
                } else {
                    view = new ArtistListView(parent.getContext());
                }
                view.setArtistData((ArtistListData)items.get(position));
                view.setOnChoiceItemClickListener(this);
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
                    view.setShopData((ShopListData) items.get(position));
                    view.setOnChoiceItemClickListener(this);
                    return view;
                }
            }
        }

    OnChoiceItemClickListener itemClickListener;

    public void setOnChoiceClickListener(OnChoiceItemClickListener listener){
        itemClickListener = listener;
    }
    @Override
    public void OnArtistListClick(View v, ArtistListData artistListData) {
        if(itemClickListener != null){
            itemClickListener.OnArtistListClick(v, artistListData);
        }
    }

    @Override
    public void OnShopListClick(View v, ShopListData shopListData) {
        if(itemClickListener != null){
            itemClickListener.OnShopListClick(v, shopListData);
        }
    }
}
