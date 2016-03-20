package com.didimdol.skt.kimjh.onix;

import android.app.Application;
import android.content.Context;
import com.facebook.FacebookSdk;

/**
 * Created by kimjh on 2016-03-04.
 */
public class MyApplication  extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context =this;
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static Context getContext(){
        return context;
    }
}
