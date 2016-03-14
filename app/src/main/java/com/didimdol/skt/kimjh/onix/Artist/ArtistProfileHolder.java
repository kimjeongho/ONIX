package com.didimdol.skt.kimjh.onix.Artist;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-25.
 */
public class ArtistProfileHolder extends RecyclerView.ViewHolder {
    ImageView artistImageView;
    TextView artistNameView;
    TextView shopNameView;
    TextView artistContent;
//    DetailArtistData data;
    ArtistTotalData data;
    public ArtistProfileHolder(View itemView) {
        super(itemView);
        artistImageView = (ImageView)itemView.findViewById(R.id.image_artist);
        artistNameView = (TextView)itemView.findViewById(R.id.text_artist);
        shopNameView = (TextView)itemView.findViewById(R.id.text_shop);
        artistContent = (TextView)itemView.findViewById(R.id.text_content);
    }

   /* public void setArtistProfileItem(DetailArtistData data){
        this.data = data;
        if(!TextUtils.isEmpty(data.artistImage))  {
            Glide.with(itemView.getContext())
                    .load(data.artistImage)
                    .into(artistImageView);
        }
        artistNameView.setText(data.artistName);
        shopNameView.setText(data.shopName);
        artistContent.setText(data.artistContent);
    }*/
   public void setArtistProfileItem(ArtistTotalData data){
        this.data = data;
        if(!TextUtils.isEmpty(data.artistImage))  {
            Glide.with(itemView.getContext())
                    .load(data.artistImage)
                    .into(artistImageView);
        }
        artistNameView.setText(data.artistName);
        shopNameView.setText(data.shopName);
        artistContent.setText(data.artistContent);
    }

}
