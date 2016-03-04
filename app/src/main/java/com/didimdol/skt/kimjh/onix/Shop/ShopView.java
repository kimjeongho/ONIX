package com.didimdol.skt.kimjh.onix.Shop;

import android.content.Context;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-23.
 */
public class ShopView extends FrameLayout {
    public ShopView(Context context) {
        super(context);
        init();
    }

    ImageView shopView;
    TextView shopNameView, shopChoiceView, locationView;
    ShopData shopData;


    public void init() {
        inflate(getContext(), R.layout.item_shop,this);
        shopView = (ImageView)findViewById(R.id.image_shop);
        shopNameView = (TextView)findViewById(R.id.text_shop);
        shopChoiceView = (TextView)findViewById(R.id.text_like);
        locationView = (TextView)findViewById(R.id.text_location);
    }

    public void setShopData(ShopData data) {
        this.shopData = data;
        if(!TextUtils.isEmpty(data.shopImage))  {
            Glide.with(getContext())
                    .load(data.shopImage)
                    .into(shopView);
        }
        shopNameView.setText(data. shopName);
        shopChoiceView.setText(data. shopChoice);
        locationView.setText(data.location);
    }
}
