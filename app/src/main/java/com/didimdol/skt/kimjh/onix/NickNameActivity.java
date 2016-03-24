package com.didimdol.skt.kimjh.onix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.NickNameSuccess;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;

import okhttp3.Request;

public class NickNameActivity extends AppCompatActivity {

    EditText editNickname;
    TextView textNickName;
    NickNameSuccess data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);

        editNickname = (EditText)findViewById(R.id.edit_nickname);
        textNickName = (TextView)findViewById(R.id.text_nickname);

        Button btn = (Button)findViewById(R.id.btn_nickname);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initChangeData();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initChangeData() {
        String changeNichname = editNickname.getText().toString();
        NetworkManager.getInstance().getArtistNickNameChangeResult(this,changeNichname , new NetworkManager.OnResultListener<NickNameSuccess>() {
            @Override
            public void onSuccess(Request request, NickNameSuccess result) {
                initData();
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

    private void initData() {
        NetworkManager.getInstance().getArtistNickNameResult(this, new NetworkManager.OnResultListener<NickNameSuccess>() {
            @Override
            public void onSuccess(Request request, NickNameSuccess result) {
                Toast.makeText(NickNameActivity.this, "success: " + result.message, Toast.LENGTH_SHORT).show();
                setDate(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(NickNameActivity.this, " " + cause, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDate(NickNameSuccess data) {
        this.data = data;
        textNickName.setText(data.nickname);
    }
}
