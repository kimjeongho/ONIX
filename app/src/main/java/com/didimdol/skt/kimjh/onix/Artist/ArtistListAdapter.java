package com.didimdol.skt.kimjh.onix.Artist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListSuccess;

import java.util.ArrayList;

/**
 * Created by kimjh on 2016-02-23.
 */
public class ArtistListAdapter extends BaseAdapter {
    ArrayList<ArtistListData> items = new ArrayList<ArtistListData>();

    public ArtistListAdapter(){
    }

   /* public void add(ArtistTotalData data){
        items.add(data);
        notifyDataSetChanged();
    }*/
    public void set(ArtistListSuccess data){
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
        ArtistListView view;
        if(convertView == null){
            view = new ArtistListView(parent.getContext());
        } else {
            view = (ArtistListView)convertView;
        }
        view.setArtistData(items.get(position));
        return view;

    }
}
