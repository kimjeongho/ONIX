package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimjh on 2016-02-27.
 */
//샵페이지 아티스트 리스트 데이터
public class ShopArtistListData implements Serializable {
//    public int artistListImage;
    @SerializedName("artist_id")
    public String artistId;
    @SerializedName("artistNickname")
    public String artistName;
    @SerializedName("intro")
    public String artistContent;
    @SerializedName("artistProfilePhoto")
    public String artistListImage;
    @SerializedName("artistJJim_counts")
    public String artistChoice;

}
