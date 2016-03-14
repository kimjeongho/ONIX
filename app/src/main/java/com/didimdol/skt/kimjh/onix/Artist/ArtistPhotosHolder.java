package com.didimdol.skt.kimjh.onix.Artist;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.OnArtistItemClickListener;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-03-02.
 */
public class ArtistPhotosHolder extends RecyclerView.ViewHolder {
    ImageView artistPhotos;
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
        artistPhotos = (ImageView)itemView.findViewById(R.id.test_image);
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
        if(!TextUtils.isEmpty(data.artistPhotos.get(0)))  {
            Glide.with(itemView.getContext())
                    .load(data.artistPhotos.get(0))
                    .into(artistPhotos);
        }
        if(mData.choiceSort == 1){
            artistChoiceBtn.setImageResource(R.drawable.btn_like_it_pre);
        } else {
            artistChoiceBtn.setImageResource(R.drawable.btn_like_it_nor);
        }

    }



}
