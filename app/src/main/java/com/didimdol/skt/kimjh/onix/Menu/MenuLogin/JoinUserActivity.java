package com.didimdol.skt.kimjh.onix.Menu.MenuLogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.JoinResult;
import com.didimdol.skt.kimjh.onix.DataClass.JoinSuccess;
import com.didimdol.skt.kimjh.onix.MainActivity;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import okhttp3.Request;

public class JoinUserActivity extends AppCompatActivity {
    ImageButton joinFinishView;
    EditText emailView;
    EditText passwordView;
    EditText repasswordView;
    JoinSuccess joinSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_user);

        emailView = (EditText) findViewById(R.id.edit_join_email);
        passwordView = (EditText) findViewById(R.id.edit_join_password);
        repasswordView = (EditText) findViewById(R.id.edit_join_repassword);



        joinFinishView = (ImageButton) findViewById(R.id.btn_join_finish);
        joinFinishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(repassword)) {
                    if (password.equals(repassword)) {*/
                        /*Intent intent = new Intent(JoinUserActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);*/
                        initDate();
                    /*} else {
                        Snackbar.make(v, "비밀번호가 일치 하지 않습니다.", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(v, "전체 작성 하셔야 됩니다.", Snackbar.LENGTH_SHORT).show();
                }*/


            }
        });


    }

    private void initDate() {
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String repassword = repasswordView.getText().toString();
        if (password.equals(repassword)) {
            NetworkManager.getInstance().setJoinResult(JoinUserActivity.this, email, password, new NetworkManager.OnResultListener<JoinResult>() {
                @Override
                public void onSuccess(Request request, JoinResult result) {
                    if (result.failResult == null) {
                        Toast.makeText(JoinUserActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                    } else if (result.failResult != null) {
                        Toast.makeText(JoinUserActivity.this, "fail: " + result.failResult.message, Toast.LENGTH_SHORT).show();
                    }
//                    result.message
//                    Toast.makeText(JoinUserActivity.this,result.message,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {
                    Toast.makeText(JoinUserActivity.this, "failer", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(JoinUserActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
