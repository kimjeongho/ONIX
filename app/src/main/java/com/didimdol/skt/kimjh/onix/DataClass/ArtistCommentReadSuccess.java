package com.didimdol.skt.kimjh.onix.DataClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-03-24.
 */
public class ArtistCommentReadSuccess {
    public String message;
    public int commentpage;
    public int listPerPage;
    public List<ArtistCommentData> comments = new ArrayList<ArtistCommentData>();
}
