package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimjh on 2016-03-08.
 */
public class ArtistListData implements ChoiceData, Serializable {
    //아티스트 리스트 데이터& 찜 내역 데이터

    @SerializedName("artist_id")
    public int artistId;        //아티스트 아이디

    @SerializedName("artistNickname")
    public String artistName;   //artist이름

    @SerializedName("discount")
    public int artistDiscount; // 할인율

    @SerializedName("artist_jjim_counts")
    public String artistChoice;         //찜 목록수

    @SerializedName("mainPhoto")
    public String artistImage;          //아티스트 대표 이미지
}
