package com.didimdol.skt.kimjh.onix.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.LoginFacebookResult;
import com.didimdol.skt.kimjh.onix.DataClass.PushResult;
import com.didimdol.skt.kimjh.onix.MainActivity;
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
    TimePicker startTime;
    TimePicker finishTime;
    Spinner discountView;
    ImageView pushBtn;
    String start;
    String finish;
    int discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        ImageView onixHome = (ImageView) findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(PushActivity.this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();
            }
        });

        discountView = (Spinner)findViewById(R.id.spin_discount);
        startTime = (TimePicker)findViewById(R.id.time_start);
        finishTime = (TimePicker)findViewById(R.id.time_finish);

        startTime.setIs24HourView(true);
        startTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 10) {
                    start = "0" + hourOfDay + ":" + minute;
                } else if (minute < 10) {
                    start = hourOfDay + ":" + "0" + minute;
                } else {
                    start = hourOfDay + ":" + minute;
                }
            }
        });

        finishTime.setIs24HourView(true);
       finishTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
           @Override
           public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
               if (hourOfDay < 10){
                   finish = "0"+hourOfDay + ":" + minute;}
               else if (minute < 10){
                   finish = hourOfDay + ":" + "0"+minute;
               } else {
                   finish = hourOfDay + ":" +minute;
               }
           }
       });

        discountView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                discount = Integer.parseInt((String) discountView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pushBtn = (ImageView)findViewById(R.id.image_push);
        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(PushActivity.this, validTime,Toast.LENGTH_SHORT).show();
                initData(discount,start,finish);
            }
        });

    }

    private void initData(final int discount, final String start, final String finish) {

        NetworkManager.getInstance().setPushResult(this, discount, start+"~"+finish+"까지", new NetworkManager.OnResultListener<PushResult>() {
            @Override
            public void onSuccess(Request request, PushResult result) {
                if (result.failResult == null) {
                    Toast.makeText(PushActivity.this,"success: "+result.successResult,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PushActivity.this,""+result.failResult,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(PushActivity.this, "fail " +cause, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
