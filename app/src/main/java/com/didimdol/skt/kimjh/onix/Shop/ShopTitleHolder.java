package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.DataClass.DetailShopData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-27.
 */
public class ShopTitleHolder extends RecyclerView.ViewHolder {
    TextView shopTitleView;
    DetailShopData data;
    public ShopTitleHolder(View itemView) {
        super(itemView);
        shopTitleView = (TextView)itemView.findViewById(R.id.text_shopname);
    }
    public void setTitleItem(DetailShopData data)
    {
        this.data = data;
        shopTitleView.setText(data.shopTitle);
    }
}
