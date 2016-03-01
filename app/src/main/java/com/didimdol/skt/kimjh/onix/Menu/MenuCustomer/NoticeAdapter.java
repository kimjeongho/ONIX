package com.didimdol.skt.kimjh.onix.Menu.MenuCustomer;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-26.
 */
public class NoticeAdapter extends BaseExpandableListAdapter {
    List<NoticeGroup> items = new ArrayList<NoticeGroup>();

    public void put(String noticeGroup, String noticeChild){
        NoticeGroup match = null;
        for(NoticeGroup g: items){
            if(g.noticeGroup.equals(noticeGroup)){
                match = g;
                break;
            }
        }

        if(match == null){
            match = new NoticeGroup();
            match.noticeGroup = noticeGroup;
            items.add(match);
        }
        if(!TextUtils.isEmpty(noticeChild)){
            NoticeChild child = new NoticeChild();
            child.noticeChild = noticeChild;
            match.children.add(child);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return items.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ((long)groupPosition)<<32|0xFFFFFFFF;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return ((long)groupPosition)<<32|childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        NoticeGroupView view;
        if(convertView == null){
            view = new NoticeGroupView(parent.getContext());
        } else {
            view = (NoticeGroupView)convertView;
        }
        view.setGroupItem(items.get(groupPosition));
//        if(isExpanded)

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        NoticeChildView view;
        if(convertView == null){
            view = new NoticeChildView(parent.getContext());
        } else {
            view = (NoticeChildView)convertView;
        } view.setChildItem(items.get(groupPosition).children.get(childPosition));

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
