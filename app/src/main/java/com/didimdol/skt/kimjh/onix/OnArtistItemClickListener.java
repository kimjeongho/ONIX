package com.didimdol.skt.kimjh.onix;

import android.view.View;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;

/**
 * Created by kimjh on 2016-03-02.
 */
public interface OnArtistItemClickListener {
    //아티스트 화면 클릭 이벤트
    public void onShopClick(View view, ArtistTotalData artistTotalData);
    public void onChoiceClick(View view, ArtistTotalData artistTotalData);
}
