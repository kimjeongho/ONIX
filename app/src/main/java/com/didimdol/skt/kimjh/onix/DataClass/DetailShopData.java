package com.didimdol.skt.kimjh.onix.DataClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-22.
 */
public class DetailShopData {
    public int shopPhotos;
    public List<ArtistListData> artistListDatas = new ArrayList<ArtistListData>();
    public String shopTitle;
//    public String address ;
    public List<ShopTiemData> shopTimeDatas = new ArrayList<ShopTiemData>();
    public List<ShopLocationData> shopLocation = new ArrayList<ShopLocationData>();
}

