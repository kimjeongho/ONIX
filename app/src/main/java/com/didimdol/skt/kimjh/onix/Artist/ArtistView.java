package com.didimdol.skt.kimjh.onix.Artist;

import android.content.Context;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalSuccess;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-23.
 */
public class ArtistView extends FrameLayout {
    public ArtistView(Context context) {
        super(context);
        init();
    }

    ImageView artistView;
    ImageView artistDiscountview;
    TextView artistNameView, artistChoiceView, locationView;
    ArtistTotalData artistData;
//    ArtistTotalSuccess artistData;


    public void init() {
        inflate(getContext(), R.layout.item_artist,this);
        artistDiscountview = (ImageView)findViewById(R.id.image_discount);
        artistView = (ImageView)findViewById(R.id.image_shop);
        artistNameView = (TextView)findViewById(R.id.text_shop);
        artistChoiceView = (TextView)findViewById(R.id.text_like);
        locationView = (TextView)findViewById(R.id.text_location);
    }

    public void setArtistData(ArtistTotalData data) {
        this.artistData = data;
        if(!TextUtils.isEmpty(data.artistPhotos.get(0)))  {
            Glide.with(getContext())
                    .load(data.artistPhotos.get(0))
                    .into(artistView);
        }

        if(!TextUtils.isEmpty(data.artistDiscount))  {
            Glide.with(getContext())
                    .load(data.artistDiscount)
                    .into(artistDiscountview);
        }
        artistNameView.setText(data.artistName);
        artistChoiceView.setText(data.artistChoice);
    }
    /*public void setArtistData(ArtistTotalSuccess data) {
        this.artistData = data;
        if(!TextUtils.isEmpty(data.artistsList.)  {
            Glide.with(getContext())
                    .load(data.artistImage)
                    .into(artistView);
        }

        if(!TextUtils.isEmpty(data.artistDiscount))  {
            Glide.with(getContext())
                    .load(data.artistDiscount)
                    .into(artistDiscountview);
        }
        artistNameView.setText(data.artistName);
        artistChoiceView.setText(data.artistChoice);
    }*/
}
