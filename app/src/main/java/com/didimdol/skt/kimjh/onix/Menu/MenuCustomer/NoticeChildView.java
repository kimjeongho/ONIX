package com.didimdol.skt.kimjh.onix.Menu.MenuCustomer;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-26.
 */
public class NoticeChildView extends FrameLayout {

    public NoticeChildView(Context context) {
        super(context);
        init();
    }

    TextView childView;
    NoticeChild item;
    private void init() {
        inflate(getContext(), R.layout.item_notice_child, this);
        childView = (TextView)findViewById(R.id.text_child);
    }

    public void setChildItem(NoticeChild item) {
        this.item = item;
        childView.setText(item.noticeChild);
    }
}
