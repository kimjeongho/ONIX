package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kimjh on 2016-02-22.
 */
public class DiscountListData {
    //public String DiscountImage;
    @SerializedName("artist_id")
    public int artistId;

    @SerializedName("artistNickname")
    public String artistName;

    @SerializedName("artistProfilePhoto")
    public String discountImage;

    @SerializedName("shop_id")
    public int shopId;
    public String shopName;

    @SerializedName("register_date")
    public String discountYear;

    @SerializedName("validDate")
    public String discountTime; //!까지 정한 시간 나오는 view

    @SerializedName("discount")
    public String discountPercent;

    /*public String discountMonth;
    public String discountDay;*/
}
