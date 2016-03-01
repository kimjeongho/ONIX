package com.didimdol.skt.kimjh.onix.Board;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.didimdol.skt.kimjh.onix.DataClass.BoardData;

import java.util.ArrayList;

/**
 * Created by kimjh on 2016-02-23.
 */
public class BoardAdapter extends BaseAdapter {
    ArrayList<BoardData> items = new ArrayList<BoardData>();

    public BoardAdapter(){
    }

    public void add(BoardData data){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        BoardWriterView view;
        if(convertView == null){
            view = new BoardWriterView(parent.getContext());
        } else {
            view = (BoardWriterView)convertView;
        }
        view.setBoardWriteItem(items.get(position));
        return view;

    }
}
