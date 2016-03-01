package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.didimdol.skt.kimjh.onix.DataClass.ShopLocationData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-27.
 */
public class ShopLocationHolder extends RecyclerView.ViewHolder {
    ImageView mapView;
    ImageView expandView;
    ImageView redusView;
    long locationLatitude;
    long locationLongitude;
    ShopLocationData data;
    public ShopLocationHolder(View itemView) {
        super(itemView);
        mapView = (ImageView)itemView.findViewById(R.id.image_map);
        expandView = (ImageView)itemView.findViewById(R.id.image_expand);
        redusView = (ImageView)itemView.findViewById(R.id.image_redus);
    }

    public void setMapView (ShopLocationData data){
        this.data = data;
    }
}
