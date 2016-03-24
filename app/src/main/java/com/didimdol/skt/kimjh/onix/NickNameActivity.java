package com.didimdol.skt.kimjh.onix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


        initData();
    }

    private void initData() {
        NetworkManager.getInstance().getArtistNickNameResult(this, new NetworkManager.OnResultListener<NickNameSuccess>() {
            @Override
            public void onSuccess(Request request, NickNameSuccess result) {
                    Toast.makeText(NickNameActivity.this, "success: " + result.message, Toast.LENGTH_SHORT).show();
                textNickName.setText(data.nickname);
                textNickName.getText().toString();
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(NickNameActivity.this, " " + cause, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
