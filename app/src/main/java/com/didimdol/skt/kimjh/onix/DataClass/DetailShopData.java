package com.didimdol.skt.kimjh.onix.DataClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-22.
 */
public class DetailShopData {
    public List<String> shopPhotos;
    public List<ShopArtistListData> artistListDatas = new ArrayList<ShopArtistListData>();
    public String shopTitle;
//    public String address ;
    public ShopTiemData shopTimeDatas;
    public ShopLocationData shopLocation;
}

