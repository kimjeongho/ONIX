package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.DetailShopData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.OnShopItemClickListener;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-03-02.
 */
public class ShopPhotosHolder extends RecyclerView.ViewHolder {
    ImageView shopPhotos;
    ImageView callBtn;
    ImageView shopChoiceBtn;

    public OnShopItemClickListener itemClickListener;
    public void setOnShopItemClickListener(OnShopItemClickListener listener){
        itemClickListener = listener;
    }

    public ShopPhotosHolder(View itemView) {
        super(itemView);
        shopPhotos = (ImageView)itemView.findViewById(R.id.test_image);
        callBtn = (ImageView)itemView.findViewById(R.id.btn_call);
        shopChoiceBtn = (ImageView)itemView.findViewById(R.id.btn_choice);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onCallClick(v, mData);
                }
            }
        });

        shopChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onChoiceClick(v, mData);
                }
            }
        });
    }

    ShopTotalData mData;
    public void setShopPhotosItem(ShopTotalData data){
        mData = data;
        if(!TextUtils.isEmpty(data.shopPhotos.get(0)))  {
            Glide.with(itemView.getContext())
                    .load(data.shopPhotos.get(0))
                    .into(shopPhotos);
        }
        if(mData.choiceSort == 1){
            shopChoiceBtn.setImageResource(R.drawable.btn_like_it_pre);
        } else {
            shopChoiceBtn.setImageResource(R.drawable.btn_like_it_nor);
        }
    }

}
