package com.didimdol.skt.kimjh.onix.Menu.MenuDiscount;

import android.content.Context;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.DiscountListData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-28.
 */
public class DiscountView extends FrameLayout {

    ImageView artistImage;
    TextView artistNameView;
    TextView shopNameView;
    TextView discountTimeView;
    TextView discountView;
    TextView yearView;
    TextView monthView;
    TextView dayView;
    DiscountListData discountData;

    public DiscountView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_discount,this);
        artistImage = (ImageView)findViewById(R.id.image_artist);
        artistNameView = (TextView)findViewById(R.id.text_artist);
        shopNameView = (TextView)findViewById(R.id.text_shop);
        discountTimeView = (TextView)findViewById(R.id.text_time);
        discountView = (TextView)findViewById(R.id.text_discount);
        yearView = (TextView)findViewById(R.id.text_year);
       /* monthView = (TextView)findViewById(R.id.text_month);
        dayView = (TextView)findViewById(R.id.text_day);*/
    }

    public void setDiscountItem(DiscountListData data){
        this.discountData = data;
        if(!TextUtils.isEmpty(data.discountImage))  {
            Glide.with(getContext())
                    .load(data.discountImage)
                    .into(artistImage);
        }
        artistNameView.setText(data.artistName);
        shopNameView.setText(data.shopName);
        discountTimeView.setText(data.discountTime);
        discountView.setText(data.discountPercent+"%");
        yearView.setText(data.discountYear);
       /* monthView.setText(data.discountMonth);
        dayView.setText(data.discountDay);*/
    }





}
