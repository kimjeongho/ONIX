package com.didimdol.skt.kimjh.onix.Menu.MenuDiscount;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.didimdol.skt.kimjh.onix.DataClass.DiscountListData;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountListSuccess;

import java.util.ArrayList;

/**
 * Created by kimjh on 2016-02-28.
 */
public class DiscountAdapter extends BaseAdapter {
    ArrayList<DiscountListData> items = new ArrayList<DiscountListData>();

    public DiscountAdapter() {

    }

    /*public void add(DiscountListData data) {
        items.add(data);
        notifyDataSetChanged();
    }*/


    public void set(DiscountListSuccess data){
        items.addAll(data.discountList);
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
        DiscountView view;
        if (convertView == null) {
            view = new DiscountView(parent.getContext());
        } else {
            view = (DiscountView) convertView;
        }
        view.setDiscountItem(items.get(position));
        return view;

    }
}
