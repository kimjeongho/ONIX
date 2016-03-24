package com.didimdol.skt.kimjh.onix.Shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.didimdol.skt.kimjh.onix.DataClass.DetailShopData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.OnShopItemClickListener;
import com.didimdol.skt.kimjh.onix.R;

import java.util.HashMap;

/**
 * Created by kimjh on 2016-03-02.
 */
public class ShopPhotosHolder extends RecyclerView.ViewHolder implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
//    ImageView shopPhotos;
    SliderLayout shopPhotos;
    ImageView callBtn;
    ImageView shopChoiceBtn;

    public OnShopItemClickListener itemClickListener;
    public void setOnShopItemClickListener(OnShopItemClickListener listener){
        itemClickListener = listener;
    }

    public ShopPhotosHolder(View itemView) {
        super(itemView);
//        shopPhotos = (ImageView)itemView.findViewById(R.id.test_image);
        shopPhotos = (SliderLayout)itemView.findViewById(R.id.slider_image);

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
        /*if(!TextUtils.isEmpty(data.shopPhotos.get(0)))  {
            Glide.with(itemView.getContext())
                    .load(data.shopPhotos.get(0))
                    .into(shopPhotos);
        }*/
        /*if(!TextUtils.isEmpty((CharSequence) data.shopPhotos))  {
            Glide.with(itemView.getContext())
                    .load(data.shopPhotos)
                    .into(shopPhotos);
        }*/
        HashMap<String,String> url_maps = new HashMap<String, String>();
        for (int i = 0; i < data.shopPhotos.size(); i++){
            url_maps.put("photos"+i, data.shopPhotos.get(i));
        }
        shopPhotos.removeAllSliders();
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(itemView.getContext());
            // initialize a SliderLayout
            textSliderView.image(url_maps.get(name));

            shopPhotos.addSlider(textSliderView);
            shopPhotos.setPresetTransformer(SliderLayout.Transformer.Default);
            shopPhotos.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        }

        if(mData.choiceSort == 1){
            shopChoiceBtn.setImageResource(R.drawable.btn_like_it_pre);
        } else {
            shopChoiceBtn.setImageResource(R.drawable.btn_like_it_nor);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
