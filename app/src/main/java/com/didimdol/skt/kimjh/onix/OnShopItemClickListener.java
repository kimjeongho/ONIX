package com.didimdol.skt.kimjh.onix;

import android.view.View;

import com.didimdol.skt.kimjh.onix.DataClass.ShopArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;

/**
 * Created by kimjh on 2016-03-02.
 */
public interface OnShopItemClickListener {
    //샵 화면 클릭 이벤트
    public void onCallClick(View view, ShopTotalData shopTotalData);
    public void onChoiceClick(View view, ShopTotalData shopTotalData);
    public void onArtistListClick(View view, ShopArtistListData shopArtistListData);
}
