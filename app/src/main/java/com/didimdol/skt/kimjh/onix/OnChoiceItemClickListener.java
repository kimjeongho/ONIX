package com.didimdol.skt.kimjh.onix;

import android.view.View;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopListData;

/**
 * Created by kimjh on 2016-03-11.
 */
public interface OnChoiceItemClickListener {
    public void OnArtistListClick(View v,ArtistListData artistListData);
    public void OnShopListClick(View v, ShopListData shopListData);
}
