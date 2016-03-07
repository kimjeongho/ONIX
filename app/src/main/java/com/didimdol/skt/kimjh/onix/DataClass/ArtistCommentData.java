package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kimjh on 2016-02-22.
 */
public class ArtistCommentData implements Serializable {
    @SerializedName("writer")
    public String userName;
    @SerializedName("content")
    public String userComment;

    @SerializedName("register_date")
    public String userDate;

    public ArtistCommentData(){

    }

    public ArtistCommentData(String userId, String userComment ,String userDate){
        this.userName = userId;
        this.userComment = userComment;
        this.userDate =userDate;
    }
}
