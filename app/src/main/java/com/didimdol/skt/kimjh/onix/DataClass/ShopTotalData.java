package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-22.
 */
public class ShopTotalData implements ChoiceData, Serializable {
//    public int iconid;
    @SerializedName("shop_id")
    public int shopId;
    public String shopName;
    @SerializedName("address")
    public String shopAddress;

    @SerializedName("shop_jjim_counts")
    public String shopChoice;         //찜 목록수

    @SerializedName("jjim_status")
    public int choiceSort;   // 찜상태 0 or 1

    public String longitude;    //경도
    public String latitude;     //위도

    @SerializedName("callnumber")
    public String callNumber; // 전화번호

    @SerializedName("usetime")
    public String useTime;     // 이용시간

    public List<String> shopPhotos;

    @SerializedName("attArtists")
    public List<ShopArtistListData> artistListDatas = new ArrayList<ShopArtistListData>();

    public String location;// 샵과 내위치 거리 구하기
}
