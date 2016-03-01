package com.didimdol.skt.kimjh.onix.DataClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-22.
 */
public class BoardData {
    public int iconid;
    public String boardTitle;
    public String boardName;
    public String boardTime;
    public String boardCategory;
    public String boardContent;
    public List<BoardCommentData> boardCommentDatas = new ArrayList<BoardCommentData>();
}
