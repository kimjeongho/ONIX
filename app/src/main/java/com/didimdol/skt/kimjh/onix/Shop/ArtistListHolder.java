package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.OnShopItemClickListener;
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

    public OnShopItemClickListener itemClickListener;
    public void setOnShopItemClickListener(OnShopItemClickListener listener){
        itemClickListener = listener;
    }

    public ArtistListHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onArtistListClick(v,getAdapterPosition());
                }
            }
        });

        artistImageView = (ImageView)itemView.findViewById(R.id.image_artist);
        artistNameView = (TextView)itemView.findViewById(R.id.text_artistname);
        shopNameView = (TextView)itemView.findViewById(R.id.text_shopname);
        artistSortView = (TextView)itemView.findViewById(R.id.text_sort);
        artistContentView = (TextView)itemView.findViewById(R.id.text_content);
        artistChoice = (TextView)itemView.findViewById(R.id.text_artistcount);
    }


    public void setArtistListItem(ArtistListData data){
        this.data =data;
        if(!TextUtils.isEmpty(data.artistListImage))  {
            Glide.with(itemView.getContext())
                    .load(data.artistListImage)
                    .into(artistImageView);
        }
        artistNameView.setText(data.artistName);
        shopNameView.setText(data.shopName);
        artistSortView.setText(data.artistSort);
        artistContentView.setText(data.artistContent);
        artistChoice.setText(""+data.artistChoice);
    }
}
