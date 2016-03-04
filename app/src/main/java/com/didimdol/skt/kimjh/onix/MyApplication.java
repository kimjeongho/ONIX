package com.didimdol.skt.kimjh.onix;

import android.app.Application;
import android.content.Context;

/**
 * Created by kimjh on 2016-03-04.
 */
public class MyApplication  extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context =this;
    }

    public static Context getContext(){
        return context;
    }
}
