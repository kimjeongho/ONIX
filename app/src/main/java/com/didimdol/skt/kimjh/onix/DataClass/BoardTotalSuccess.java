package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-03-07.
 */
public class BoardTotalSuccess {
    public String message;
    public int page;
    public int listPerPage;
    public String boardName;

    @SerializedName("postList")
    public List<BoardData> boardList = new ArrayList<BoardData>();

}
