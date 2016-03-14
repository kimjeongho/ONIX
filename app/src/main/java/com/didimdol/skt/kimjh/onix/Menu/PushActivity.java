package com.didimdol.skt.kimjh.onix.Menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.didimdol.skt.kimjh.onix.R;

public class PushActivity extends AppCompatActivity {

    TimePicker startTime;
    TimePicker finishTime;
    Spinner discountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        discountView = (Spinner)findViewById(R.id.spin_discount);
        startTime = (TimePicker)findViewById(R.id.time_start);
        finishTime = (TimePicker)findViewById(R.id.time_finish);

        startTime.setIs24HourView(true);
        finishTime.setIs24HourView(true);
    }
}
