package com.didimdol.skt.kimjh.onix.Menu.MenuCustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.didimdol.skt.kimjh.onix.R;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ImageButton btn = (ImageButton)findViewById(R.id.btn_notice);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

        btn = (ImageButton)findViewById(R.id.btn_terms);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, TermsActivity.class);
                startActivity(intent);
            }
        });


    }
}
