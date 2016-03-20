package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.DataClass.ShopLocationData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.OnShopItemClickListener;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-27.
 */
public class ShopLocationHolder extends RecyclerView.ViewHolder {
    ImageView mapView;
    ImageView expandView;
    ImageView redusView;
    TextView locationLatitude;
    TextView locationLongitude;
    ShopTotalData mData;

    public OnShopItemClickListener itemClickListener;
    public void setOnShopItemClickListener(OnShopItemClickListener listener){
        itemClickListener = listener;
    }
    public ShopLocationHolder(View itemView) {
        super(itemView);
        mapView = (ImageView)itemView.findViewById(R.id.image_map);
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onShopMapClick(v, mData);
                }
            }
        });
        expandView = (ImageView)itemView.findViewById(R.id.image_expand);
        redusView = (ImageView)itemView.findViewById(R.id.image_redus);
        locationLatitude = (TextView)itemView.findViewById(R.id.latitude);
        locationLongitude = (TextView)itemView.findViewById(R.id.longitude);
    }

    public void setMapView (ShopTotalData data){
        this.mData = data;
        locationLatitude.setText(data.latitude);
        locationLongitude.setText(data.longitude);
    }
}
