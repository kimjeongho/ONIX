package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-22.
 */
public class BoardData implements Serializable {
//    public int iconid;

    @SerializedName("post_id")
    public int postId;

    @SerializedName("writer_id")
    public int writerId;


    public String writer;      //글쓴이

    @SerializedName("register_date")
    public String boardTime;

    @SerializedName("boardName")
    public String boardCategory;

    @SerializedName("title")
    public String boardTitle;

    @SerializedName("content")
    public String boardContent;

    @SerializedName("boardPhoto")
    public String boardImage;


/*
    @SerializedName("replies")
    public List<BoardCommentData> boardCommentDatas = new ArrayList<BoardCommentData>();*/
}
