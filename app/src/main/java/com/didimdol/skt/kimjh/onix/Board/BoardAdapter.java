package com.didimdol.skt.kimjh.onix.Board;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.DataClass.BoardTotalSuccess;

import java.util.ArrayList;

/**
 * Created by kimjh on 2016-02-23.
 */
public class BoardAdapter extends BaseAdapter {
    ArrayList<BoardData> items = new ArrayList<BoardData>();

    public BoardAdapter(){
    }

   /* public void add(BoardData data){
        items.add(data);
        notifyDataSetChanged();
    }*/

    public void set(BoardTotalSuccess data){
        items.addAll(data.boardList);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
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
        BoardView view;
        if(convertView == null){
            view = new BoardView(parent.getContext());
        } else {
            view = (BoardView)convertView;
        }
        view.setBoardItem(items.get(position));
        return view;

    }
}
