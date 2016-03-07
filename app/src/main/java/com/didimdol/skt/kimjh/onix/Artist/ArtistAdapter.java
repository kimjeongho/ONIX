package com.didimdol.skt.kimjh.onix.Artist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalSuccess;

import java.util.ArrayList;

/**
 * Created by kimjh on 2016-02-23.
 */
public class ArtistAdapter extends BaseAdapter {
    ArrayList<ArtistTotalData> items = new ArrayList<ArtistTotalData>();

    public ArtistAdapter(){
    }

   /* public void add(ArtistTotalData data){
        items.add(data);
        notifyDataSetChanged();
    }*/
    public void set(ArtistTotalSuccess data){
        items.addAll(data.artistsList);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ArtistView view;
        if(convertView == null){
            view = new ArtistView(parent.getContext());
        } else {
            view = (ArtistView)convertView;
        }
        view.setArtistData(items.get(position));
        return view;

    }
}
