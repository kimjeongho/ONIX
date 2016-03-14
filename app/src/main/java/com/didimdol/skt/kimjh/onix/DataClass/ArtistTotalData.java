package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-22.
 */
public class ArtistTotalData implements ChoiceData, Serializable {

    public String message;

    @SerializedName("artist_id")
    public int artistId;

    @SerializedName("artistNickname")
    public String artistName;

    /*@SerializedName("discount")
    public String discountText;*/

    @SerializedName("discount")
    public int artistDiscount; // 할인율

    @SerializedName("jjim_status")
    public int choiceSort;   // 찜상태 0 or 1

    @SerializedName("intro")
    public String artistContent;    //소개글

    @SerializedName("shop_id")
    public String shopId; // 샵 아이디

    public String shopName;

    @SerializedName("artistProfilePhoto")
    public String artistImage;  //아티스트 프로필 이미지

    public List<String> artistPhotos; //아티스트 포트폴리오 이미지

    @SerializedName("services")
    public List<NailTypeData> nailType = new ArrayList<NailTypeData>();
    @SerializedName("comments")
    public List<ArtistCommentData> artistComment = new ArrayList<ArtistCommentData>();

}
