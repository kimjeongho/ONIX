package com.didimdol.skt.kimjh.onix.DataClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-03-08.
 */
public class ChoiceSuccess {
    public String message;
    public int page;
    public int listPerPage;
    public List<ArtistListData> artistsList = new ArrayList<ArtistListData>();
    public List<ShopListData> shopsList = new ArrayList<ShopListData>();

    /*public List<ChoiceArtistData> artistsList = new ArrayList<ChoiceArtistData>();
    public List<ChoiceShopData> shopsList = new ArrayList<ChoiceShopData>();*/
}
