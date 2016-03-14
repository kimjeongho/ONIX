package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-03-07.
 */
public class ArtistListSuccess implements Serializable {
    public int page;
    public int listPerPage;
    public String message;
    @SerializedName("artistList")
    public List<ArtistListData> artistsList = new ArrayList<ArtistListData>();
}
