package com.didimdol.skt.kimjh.onix.Shop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.ShopListData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.Menu.MenuChoice.ChoiceAdapter;
import com.didimdol.skt.kimjh.onix.OnChoiceItemClickListener;
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
    ShopListData shopData;

    public OnChoiceItemClickListener itemClickListener;
    public void setOnChoiceItemClickListener(ChoiceAdapter listener) {
        itemClickListener = listener;
    }

    public void init() {
        inflate(getContext(), R.layout.item_shop,this);
        shopView = (ImageView)findViewById(R.id.image_shop);
        shopNameView = (TextView)findViewById(R.id.text_shop);
        shopChoiceView = (TextView)findViewById(R.id.text_like);
        locationView = (TextView)findViewById(R.id.text_location);

        /*shopView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.OnShopListClick(v, shopData);
                }
            }
        });*/
    }

    public void setShopData(ShopListData data) {
        this.shopData = data;
        if(!TextUtils.isEmpty(data.shopImage))  {
            Glide.with(getContext())
                    .load(data.shopImage)
                    .into(shopView);
        }
        shopNameView.setText(data.shopName);
        shopChoiceView.setText(data.shopChoice + "개");
        if (data.distance > 100){
            locationView.setVisibility(GONE);
        } else {
            locationView.setVisibility(VISIBLE);
            locationView.setText(Double.toString(data.distance) + "km 이내");
        }//내 거리와 가게 거리 계산
    }

}
