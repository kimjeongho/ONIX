package com.didimdol.skt.kimjh.onix.Menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.didimdol.skt.kimjh.onix.R;

public class PushActivity extends AppCompatActivity {

    Spinner hourFirst;
    Spinner hourSecond;
    Spinner minuteFirst;
    Spinner minuteSecond;
    Spinner hourFirstEnd;
    Spinner hourSecondEnd;
    Spinner minuteFirstEnd;
    Spinner minuteSecondEnd;
    Spinner discountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        hourFirst = (Spinner)findViewById(R.id.spin_hour_first);
        hourFirstEnd = (Spinner)findViewById(R.id.spin_hour_first2);
        hourSecond = (Spinner)findViewById(R.id.spin_hour_second);
        hourSecondEnd = (Spinner)findViewById(R.id.spin_hour_second2);
        minuteFirst = (Spinner)findViewById(R.id.spin_minute_first);
        minuteFirstEnd = (Spinner)findViewById(R.id.spin_minute_first2);
        minuteSecond = (Spinner)findViewById(R.id.spin_minute_second);
        minuteSecondEnd = (Spinner)findViewById(R.id.spin_minute_second2);
        discountView = (Spinner)findViewById(R.id.spin_discount);
    }
}
