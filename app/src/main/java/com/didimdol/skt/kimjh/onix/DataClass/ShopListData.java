package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimjh on 2016-03-08.
 */
public class ShopListData implements ChoiceData, Serializable {
    //샵 리스트 데이터& 찜 내역 데이터

    @SerializedName("shop_id")
    public int shopId;        //아티스트 아이디

    public String shopName;   //샵이름

    @SerializedName("shop_jjim_counts")
    public String shopChoice;         //찜 목록수

    public double longitude;

    public double latitude;

    public double distance;

    @SerializedName("mainPhoto")
    public String shopImage;          //샵 대표 이미지
}
