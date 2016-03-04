package com.didimdol.skt.kimjh.onix.Board;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;

import com.didimdol.skt.kimjh.onix.R;

public class BoardWriteActivity extends AppCompatActivity {
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scrollView = (ScrollView)findViewById(R.id.scrollView);


    }
}
