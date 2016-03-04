package com.didimdol.skt.kimjh.onix.DataClass;

/**
 * Created by kimjh on 2016-02-22.
 */
public class BoardCommentData {
    public String boardCategory;
    public String userId;
    public String userComment;

    public BoardCommentData(){

    }

    public BoardCommentData(String userId, String userComment){
        this.userId = userId;
        this.userComment = userComment;
    }
}
