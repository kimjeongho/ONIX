package com.didimdol.skt.kimjh.onix.DataClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-03-17.
 */
public class BoardCommentReadSuccess {
    public String message;
    public int repliespage;
    public int listPerPage;
    public List<BoardCommentData> replies = new ArrayList<BoardCommentData>();
}
