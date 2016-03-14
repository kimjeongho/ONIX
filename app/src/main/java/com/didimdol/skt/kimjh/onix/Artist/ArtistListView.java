package com.didimdol.skt.kimjh.onix.Artist;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.OnArtistItemClickListener;
import com.didimdol.skt.kimjh.onix.OnChoiceItemClickListener;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-23.
 */
public class ArtistListView extends FrameLayout {
    public ArtistListView(Context context) {
        super(context);
        init();
    }

    ImageView artistView;
    ImageView artistDiscountview;
    TextView artistNameView, artistChoiceView, locationView;
    ArtistListData artistListData;

    public OnChoiceItemClickListener itemClickListener;
    public void setOnChoiceItemClickListener(OnChoiceItemClickListener listener){
        itemClickListener = listener;
    }
    public void init() {
        inflate(getContext(), R.layout.item_artist,this);
        artistDiscountview = (ImageView)findViewById(R.id.image_discount);
        artistView = (ImageView)findViewById(R.id.image_shop);
        artistNameView = (TextView)findViewById(R.id.text_shop);
        artistChoiceView = (TextView)findViewById(R.id.text_like);
        locationView = (TextView)findViewById(R.id.text_location);
       /* artistView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.OnArtistListClick(v, artistListData);
                }
            }
        });*/
    }



    public void setArtistData(ArtistListData data) {
        this.artistListData = data;
        if(!TextUtils.isEmpty(data.artistImage))  {
            Glide.with(getContext())
                    .load(data.artistImage)
                    .into(artistView);
        }

        if(data.artistDiscount !=0){
            artistDiscountview.setImageResource(R.drawable.onix_sale);
        }
        artistNameView.setText(data.artistName);
        artistChoiceView.setText(data.artistChoice);
    }
}
