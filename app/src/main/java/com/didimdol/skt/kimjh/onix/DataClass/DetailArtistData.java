package com.didimdol.skt.kimjh.onix.DataClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-22.
 */
public class DetailArtistData {
    public int artistPhotos;
//    public String artistPhotos;

    public int artistImage;
//    public String artistImage;

    public String artistName;
    public String shopName;
    public String artistContent;
    public List<NailTypeData> nailType = new ArrayList<NailTypeData>();
    public List<ArtistCommentData> artistComment = new ArrayList<ArtistCommentData>();
}
