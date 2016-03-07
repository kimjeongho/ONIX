package com.didimdol.skt.kimjh.onix.DataClass;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kimjh on 2016-02-29.
 */
public class NailTypeData implements Serializable{
    @SerializedName("type")
    public String nailType;
    @SerializedName("price")
    public String nailPrice;
}
