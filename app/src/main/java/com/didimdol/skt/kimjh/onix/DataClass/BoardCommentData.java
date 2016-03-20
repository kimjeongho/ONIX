package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimjh on 2016-02-22.
 */
public class BoardCommentData implements Serializable{

    @SerializedName("board_id")
    public int boardTypeId;

    @SerializedName("register_date")
    public String date;

    @SerializedName("write_id")
    public int boardCategory;

    @SerializedName("writer")
    public String userId;

    @SerializedName("content")
    public String userComment;

}
