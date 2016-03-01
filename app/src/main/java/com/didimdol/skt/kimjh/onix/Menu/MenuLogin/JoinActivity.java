package com.didimdol.skt.kimjh.onix.Menu.MenuLogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.didimdol.skt.kimjh.onix.R;

public class JoinActivity extends AppCompatActivity {
    ImageButton joinView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        joinView = (ImageButton)findViewById(R.id.btn_join);
        joinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, JoinUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
