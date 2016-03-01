package com.didimdol.skt.kimjh.onix.DataClass;

/**
 * Created by kimjh on 2016-02-22.
 */
public class ArtistCommentData {
    public String userId;
    public String userComment;

    public ArtistCommentData(){

    }

    public ArtistCommentData(String userId, String userComment){
        this.userId = userId;
        this.userComment = userComment;
    }
}
