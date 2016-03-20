package com.didimdol.skt.kimjh.onix.Menu.MenuLogin;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.LoginData;
import com.didimdol.skt.kimjh.onix.DataClass.LoginFacebookResult;
import com.didimdol.skt.kimjh.onix.DataClass.LoginResult;
import com.didimdol.skt.kimjh.onix.GCM.RegistrationIntentService;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.Manager.PropertyManager;
import com.didimdol.skt.kimjh.onix.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Arrays;

import okhttp3.Request;

public class LoginActivity extends AppCompatActivity {
    EditText emailEdit;
    EditText passwordEdit;
    LoginData loginData;
    LoginButton facebookBtn;
    ImageView facebookView;
    CallbackManager callbackManager;
    LoginManager loginManager;
    RadioGroup radioGroup;
    int loginType = 1;

    @Override   //로그인
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //LOGIN TYPE
        radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_user){
                    loginType =1;
                    return;
                } else if(checkedId == R.id.radio_artist) {
                    loginType =2;
                    return;
                }
            }
        });

        emailEdit= (EditText)findViewById(R.id.edit_email);
        passwordEdit = (EditText)findViewById(R.id.edit_password);
        ImageView btn = (ImageView)findViewById(R.id.btn_email);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initData();
            }
        });

        //facebook login
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();

        facebookView = (ImageView)findViewById(R.id.image_facebook);
        facebookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token == null) {
                    loginManager.registerCallback(callbackManager, new FacebookCallback<com.facebook.login.LoginResult>() {
                        @Override
                        public void onSuccess(com.facebook.login.LoginResult loginResult) {
                            AccessToken token = AccessToken.getCurrentAccessToken();
                            initFacebookData(token.getToken());

                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {

                        }
                    });
                    loginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
                    loginManager.setDefaultAudience(DefaultAudience.FRIENDS);
                    loginManager.logInWithReadPermissions(LoginActivity.this, Arrays.asList("email"));
                }
            }
        });

        //회원 가입--------------------------------------------------------------------------------------------
        btn = (ImageView)findViewById(R.id.btn_join);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,JoinUserActivity.class);
                startActivity(intent);
            }
        });


        initData();
    }

    //GCM
    private void setUpIfNeed() {

    }

    private void initFacebookData(String facebookToken) {
        NetworkManager.getInstance().setLoginFacebookResult(this, facebookToken, "", new NetworkManager.OnResultListener<LoginFacebookResult>() {
            @Override
            public void onSuccess(Request request, LoginFacebookResult result) {
                if (result.failResult == null) {
                    Toast.makeText(LoginActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                    PropertyManager.getInstance().setLogin(true);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "fail: " + result.failResult.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(LoginActivity.this, "fail: " + cause, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        String email = emailEdit.getText().toString();
        PropertyManager.getInstance().setUserId(email);
        PropertyManager.getInstance().setEmail(email);
        String passwrod = passwordEdit.getText().toString();
        PropertyManager.getInstance().setPassword(passwrod);
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(passwrod)) {
            NetworkManager.getInstance().setLogInResult(this, email, passwrod, "", loginType, new NetworkManager.OnResultListener<LoginResult>() {
                @Override
                public void onSuccess(Request request, LoginResult result) {
                    if (result.failResult == null) {
                        Toast.makeText(LoginActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                        PropertyManager.getInstance().setLogin(true);
                        finish();

                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        }
    }

}
