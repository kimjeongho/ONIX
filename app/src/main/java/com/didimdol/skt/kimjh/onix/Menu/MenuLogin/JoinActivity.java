package com.didimdol.skt.kimjh.onix.Menu.MenuLogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.LoginData;
import com.didimdol.skt.kimjh.onix.R;

public class JoinActivity extends AppCompatActivity {
    ImageButton joinView;
    CheckBox checkAll;
    CheckBox checkService;
    CheckBox checkLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        checkAll = (CheckBox)findViewById(R.id.check_all);
        checkService = (CheckBox)findViewById(R.id.check_service);
        checkLocation = (CheckBox)findViewById(R.id.check_location);
        joinView = (ImageButton) findViewById(R.id.btn_join);
        boolean isChecked=true;
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkAll.isChecked()==true ){
                    joinView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(JoinActivity.this, JoinUserActivity.class);
                            startActivity(intent);
                        }
                    });
                    checkService.setChecked(true);
                    checkLocation.setChecked(true);
                    isChecked = false;
                } else if (checkAll.isChecked()==false){
                    checkService.setChecked(false);
                    checkLocation.setChecked(false);
                    Toast.makeText(getApplicationContext(), "전체 동의 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    isChecked = true;
                }
            }
        });

    }
}
