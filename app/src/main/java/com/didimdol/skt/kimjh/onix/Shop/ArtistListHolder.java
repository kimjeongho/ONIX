package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-27.
 */
public class ArtistListHolder extends RecyclerView.ViewHolder {
    ImageView artistImageView;
    TextView artistNameView;
    TextView shopNameView;
    TextView artistSortView;
    TextView artistContentView;
    TextView artistChoice;
    ArtistListData data;

    public ArtistListHolder(View itemView) {
        super(itemView);
        artistImageView = (ImageView)itemView.findViewById(R.id.image_artist);
        artistNameView = (TextView)itemView.findViewById(R.id.text_artistname);
        shopNameView = (TextView)itemView.findViewById(R.id.text_shopname);
        artistSortView = (TextView)itemView.findViewById(R.id.text_sort);
        artistContentView = (TextView)itemView.findViewById(R.id.text_content);
        artistChoice = (TextView)itemView.findViewById(R.id.text_artistcount);
    }

    public void setArtistListItem(ArtistListData data){
        this.data =data;
        artistImageView.setImageResource(data.artistListImage);
        artistNameView.setText(data.artistName);
        shopNameView.setText(data.shopName);
        artistSortView.setText(data.artistSort);
        artistContentView.setText(data.artistContent);
        artistChoice.setText(""+data.artistChoice);
    }
}
