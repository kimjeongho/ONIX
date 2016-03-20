package com.didimdol.skt.kimjh.onix.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.LoginFacebookResult;
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
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import okhttp3.Request;

public class PushActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginManager loginManager;
    TimePicker startTime;
    TimePicker finishTime;
    Spinner discountView;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        discountView = (Spinner)findViewById(R.id.spin_discount);
        startTime = (TimePicker)findViewById(R.id.time_start);
        finishTime = (TimePicker)findViewById(R.id.time_finish);

        startTime.setIs24HourView(true);
        finishTime.setIs24HourView(true);

        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginButton = (LoginButton)findViewById(R.id.facebook_btn_test);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        Button btn  = (Button)findViewById(R.id.facebook_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token == null) {
                    loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
                    loginManager.logInWithReadPermissions(PushActivity.this, Arrays.asList("email"));
                }
            }
        });
    }

    private void initFacebookData(String facebookToken) {
        NetworkManager.getInstance().setLoginFacebookResult(this, facebookToken, "", new NetworkManager.OnResultListener<LoginFacebookResult>() {
            @Override
            public void onSuccess(Request request, LoginFacebookResult result) {
                if (result.failResult == null) {
                    Toast.makeText(PushActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PushActivity.this, "fail: " + result.failResult.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(PushActivity.this, "fail: " + cause, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
