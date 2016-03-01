package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.DataClass.ShopTiemData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-27.
 */
public class ShopTimeHolder extends RecyclerView.ViewHolder {
    TextView weekEndView;
    TextView weekDayView;
    TextView weekEtcView;
    TextView shopAddressView;
    ShopTiemData data;

    public ShopTimeHolder(View itemView) {
        super(itemView);
        weekDayView = (TextView)itemView.findViewById(R.id.text_weekday);
        weekEndView = (TextView)itemView.findViewById(R.id.text_weekend);
        weekEtcView = (TextView)itemView.findViewById(R.id.text_etc);
        shopAddressView= (TextView)itemView.findViewById(R.id.text_address);
    }

    public void setTimeItme(ShopTiemData data){
        this.data = data;
        weekDayView.setText(data.shopWeekDay);
        weekEndView.setText(data.shopWeekEnd);
        weekEtcView.setText(data.shopWeekEtc);
        shopAddressView.setText(data.shopAddress);
    }
}
