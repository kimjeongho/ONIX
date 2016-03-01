package com.didimdol.skt.kimjh.onix.Artist;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistData;
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
    TextView artistNameView, artistChoiceView, locationView;
    ArtistData artistData;


    public void init() {
        inflate(getContext(), R.layout.item_artist,this);
        artistView = (ImageView)findViewById(R.id.image_shop);
        artistNameView = (TextView)findViewById(R.id.text_shop);
        artistChoiceView = (TextView)findViewById(R.id.text_like);
        locationView = (TextView)findViewById(R.id.text_location);
    }

    public void setArtistData(ArtistData data) {
        this.artistData = data;
        artistView.setImageResource(data.iconid);
        artistNameView.setText(data.artistName);
        artistChoiceView.setText(data.artistChoice);
        locationView.setText(data.location);
    }
}
