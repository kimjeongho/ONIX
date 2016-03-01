package com.didimdol.skt.kimjh.onix.Menu.MenuCustomer;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-26.
 */
public class NoticeGroupView extends FrameLayout {
    public NoticeGroupView(Context context) {
        super(context);
        init();
    }
    TextView groupView;
    NoticeGroup item;
    private void init() {
        inflate(getContext(), R.layout.item_notice_group, this);
        groupView = (TextView)findViewById(R.id.text_group);
    }

    public void setGroupItem(NoticeGroup item) {
        this.item = item;
        groupView.setText(item.noticeGroup);
    }
}
