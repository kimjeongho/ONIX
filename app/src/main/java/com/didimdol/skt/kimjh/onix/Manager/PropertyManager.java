package com.didimdol.skt.kimjh.onix.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.didimdol.skt.kimjh.onix.MyApplication;

/**
 * Created by kimjh on 2016-02-22.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance(){
        if(instance == null){
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private static final String FIELD_USER_ID = "userId";
    private static final String FIELD_PASSWORD = "password";

    private PropertyManager(){
        Context context = MyApplication.getContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
    }

    public void setUserId(String userId){
        mEditor.putString(FIELD_USER_ID, userId);
        mEditor.commit();   //값 저장
    }

    public String getUserId(){
        return mPrefs.getString(FIELD_USER_ID,"");
    }

    boolean isLogin = false;

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    String email = "";
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password){
        mEditor.putString(FIELD_PASSWORD, password);
        mEditor.commit();   //값 저장
    }

    public String getPassword(){
        return mPrefs.getString(FIELD_PASSWORD,"");
    }

    //registrationToken
    private static final String REG_ID = "regToken";

    public void setRegistrationToken(String regId){
        mEditor.putString(REG_ID, regId);
        mEditor.commit();
    }

    public void clear(){
        mEditor.clear();
        mEditor.commit();
    }

    public String getRegistrationToken(){
        return mPrefs.getString(REG_ID, "");
    }

}

