package com.didimdol.skt.kimjh.onix.DataClass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kimjh on 2016-03-04.
 */
public class NetworkFail {
    @SerializedName("err_code")
    public int errCode;
    public String message;
}
