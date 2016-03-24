package com.didimdol.skt.kimjh.onix.Artist;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.OnArtistItemClickListener;
import com.didimdol.skt.kimjh.onix.R;

import java.util.HashMap;

/**
 * Created by kimjh on 2016-03-02.
 */
public class ArtistPhotosHolder extends RecyclerView.ViewHolder implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    SliderLayout artistPhotos;
    ImageView shopBtn;
    ImageView artistChoiceBtn;
    private boolean check = false;
    ArtistTotalData artistTotalData;

    public OnArtistItemClickListener itemClickListener;
    public void setOnArtistItemClickListener(OnArtistItemClickListener listener){
        itemClickListener = listener;
    }

    public ArtistPhotosHolder(View itemView) {
        super(itemView);
        artistPhotos = (SliderLayout)itemView.findViewById(R.id.slider_artist);
        shopBtn = (ImageView)itemView.findViewById(R.id.btn_shop);
        artistChoiceBtn = (ImageView)itemView.findViewById(R.id.btn_choice);

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onShopClick(v, mData);
                }
            }
        });

        artistChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onChoiceClick(v, mData);
                }
            }
        });
    }
    ArtistTotalData mData;
    public void setArtistPhotosItem(ArtistTotalData data){
        mData = data;
       /* if(!TextUtils.isEmpty(data.artistPhotos.get(0)))  {
            Glide.with(itemView.getContext())
                    .load(data.artistPhotos.get(0))
                    .into(artistPhotos);
        }
        if(mData.choiceSort == 1){
            artistChoiceBtn.setImageResource(R.drawable.btn_like_it_pre);
        } else {
            artistChoiceBtn.setImageResource(R.drawable.btn_like_it_nor);
        }*/
        HashMap<String,String> url_maps = new HashMap<String, String>();
        for (int i = 0; i < data.artistPhotos.size(); i++){
            url_maps.put("photos"+i, data.artistPhotos.get(i));
        }
        artistPhotos.removeAllSliders();
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(itemView.getContext());
            // initialize a SliderLayout
            textSliderView.image(url_maps.get(name));

            artistPhotos.addSlider(textSliderView);
            artistPhotos.setPresetTransformer(SliderLayout.Transformer.Default);
            artistPhotos.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        }

        if(mData.choiceSort == 1){
            artistChoiceBtn.setImageResource(R.drawable.btn_like_it_pre);
        } else {
            artistChoiceBtn.setImageResource(R.drawable.btn_like_it_nor);
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
