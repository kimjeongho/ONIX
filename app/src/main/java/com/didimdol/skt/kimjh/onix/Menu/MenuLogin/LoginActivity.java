package com.didimdol.skt.kimjh.onix.Menu.MenuLogin;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.didimdol.skt.kimjh.onix.DataClass.LoginData;
import com.didimdol.skt.kimjh.onix.R;

public class LoginActivity extends AppCompatActivity {
    EditText userEmailView;
    EditText userPasswordView;
    LoginData loginData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmailView = (EditText)findViewById(R.id.edit_email);
        userPasswordView = (EditText)findViewById(R.id.edit_password);
        String userEmail = userEmailView.getText().toString();
        String userPassword = userPasswordView.getText().toString();

        /*ActionBar actionBar = getActionBar();

        actionBar.hide();*/

    }
}
