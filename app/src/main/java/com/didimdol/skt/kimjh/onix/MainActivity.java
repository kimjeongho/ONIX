package com.didimdol.skt.kimjh.onix;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.LogoutResult;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.Manager.PropertyManager;
import com.didimdol.skt.kimjh.onix.Menu.MenuChoice.ChoiceActivity;
import com.didimdol.skt.kimjh.onix.Menu.MenuDiscount.DiscountActivity;
import com.didimdol.skt.kimjh.onix.Menu.InstructionActivity;
import com.didimdol.skt.kimjh.onix.Menu.MenuCustomer.CustomerActivity;
import com.didimdol.skt.kimjh.onix.Menu.MenuLogin.JoinActivity;
import com.didimdol.skt.kimjh.onix.Menu.MenuLogin.LoginActivity;
import com.didimdol.skt.kimjh.onix.Menu.PushActivity;
import com.didimdol.skt.kimjh.onix.TabView.TabArtist;
import com.didimdol.skt.kimjh.onix.TabView.TabBoard;
import com.didimdol.skt.kimjh.onix.TabView.TabShop;
import com.facebook.login.LoginManager;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TabLayout tabLayout;
    ViewPager pager;
    MyPagerAdapter mAdapter;
    ImageView menuHome;
    ImageView homeView;
    LoginManager loginManager;
    View headerView;
    View mainPage;
    View loginPage;
    TextView emailView;

    boolean isBackPressed = false;
    public static final int MESSAGE_BACK_KEY_TIMEOUT = 0;
    public static final int BACK_KEY_TIME = 2000;

    //backKey 2번 누를시 앱종료
    Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_BACK_KEY_TIMEOUT:
                    isBackPressed = false;
                    return true;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //tabLayout...
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        pager = (ViewPager) findViewById(R.id.pagerView);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(pager);
        tabLayout.removeAllTabs();

        TabArtist tabArtist = new TabArtist(this);
        tabArtist.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabArtist), 0);
        TabShop tabShop = new TabShop(this);
        tabShop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabShop), 1);
        TabBoard tabBoard = new TabBoard(this);
        tabBoard.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabBoard), 2);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //네비게이션 view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(R.layout.nav_header_main);
        headerView = navigationView.getHeaderView(0);

        //로그인 화면 전환
        Button btn = (Button) headerView.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //회원가입 화면 전환
        btn = (Button) headerView.findViewById(R.id.btn_join);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        //로그인후 사용자 email 나오는 화면
        emailView = (TextView) headerView.findViewById(R.id.text_email);

        //로그인전 headerview
        mainPage = headerView.findViewById(R.id.main_page);
        //로그인후 headerview
        loginPage = headerView.findViewById(R.id.login_page);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            //back 두번 누를시 종료
            if (!isBackPressed) {
                Toast.makeText(getApplicationContext(), "한번 더 누르시면 종료 됩니다.", Toast.LENGTH_SHORT).show();
                isBackPressed = true;
                mHandler.sendEmptyMessageDelayed(MESSAGE_BACK_KEY_TIMEOUT, BACK_KEY_TIME);
            } else {
                mHandler.removeMessages(MESSAGE_BACK_KEY_TIMEOUT);
                finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_customer) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_use) {
            Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_choice) {
            Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_discount) {
            Intent intent = new Intent(MainActivity.this, DiscountActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(MainActivity.this, PushActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            initData();
        }/* else if (id == R.id.nav_nickname) {
            Intent intent = new Intent(MainActivity.this, NickNameActivity.class);
            startActivity(intent);
        }*/

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuHome = (ImageView) findViewById(R.id.menu_home);
        menuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeDrawerLayout();
    }


    private void changeDrawerLayout() {
        //로그인이 된 상태
        if (PropertyManager.getInstance().isLogin()) {
            mainPage.setVisibility(View.GONE);
            loginPage.setVisibility(View.VISIBLE);
            emailView.setText(PropertyManager.getInstance().getUserId());
//            emailView.setText(PropertyManager.getInstance().getEmail());
            PropertyManager.getInstance().setLogin(true); // ??
        }
    }

    //로그아웃 할시 네트워크 요청
    private void initData() {
        NetworkManager.getInstance().setLogoutResult(this, new NetworkManager.OnResultListener<LogoutResult>() {
            @Override
            public void onSuccess(Request request, LogoutResult result) {
                if (result.failResult == null) {
//                    Toast.makeText(MainActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                    loginManager = LoginManager.getInstance();
                    loginManager.logOut();
                    PropertyManager.getInstance().setUserId("");
                    PropertyManager.getInstance().setEmail("");
                    PropertyManager.getInstance().setPassword("");
//                    PropertyManager.getInstance().clear();
                    mainPage.setVisibility(View.VISIBLE);
                    loginPage.setVisibility(View.GONE);
                    PropertyManager.getInstance().setLogin(false);  //??
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }
}

