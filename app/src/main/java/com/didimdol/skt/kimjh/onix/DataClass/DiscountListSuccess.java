package com.didimdol.skt.kimjh.onix.DataClass;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-03-10.
 */
public class DiscountListSuccess {
    public int page;
    public int listPerPage;
    public String message;
    @SerializedName("saleList")
    public List<DiscountListData> discountList = new ArrayList<DiscountListData>();
}
