package com.didimdol.skt.kimjh.onix.DataClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-03-07.
 */
public class ShopListSuccess {
    public String message;
    public int page;
    public int listPerPage;
    public List<ShopListData> shopList = new ArrayList<ShopListData>();
}
