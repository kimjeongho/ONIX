package com.didimdol.skt.kimjh.onix.TabView;

import android.content.Context;
import android.widget.FrameLayout;

import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-03-13.
 */
public class TabBoard extends FrameLayout {
    public TabBoard(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.tab_board,this);
    }
}
