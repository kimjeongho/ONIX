package com.didimdol.skt.kimjh.onix;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.ImageView;

import com.didimdol.skt.kimjh.onix.Menu.ChoiceActivity;
import com.didimdol.skt.kimjh.onix.Menu.MenuDiscount.DiscountActivity;
import com.didimdol.skt.kimjh.onix.Menu.InstructionActivity;
import com.didimdol.skt.kimjh.onix.Menu.MenuCustomer.CustomerActivity;
import com.didimdol.skt.kimjh.onix.Menu.MenuLogin.JoinActivity;
import com.didimdol.skt.kimjh.onix.Menu.MenuLogin.LoginActivity;
import com.didimdol.skt.kimjh.onix.Menu.PushActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TabLayout tabLayout;
    ViewPager pager;
    MyPagerAdapter mAdapter;
    ImageView menuHome;
    ImageView homeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*MyDialogFragment f = new MyDialogFragment();
        f.show(getSupportFragmentManager(), "dialog");
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);*/
        CustomDialogFragment f1 = new CustomDialogFragment();
        f1.show(getSupportFragmentManager(), "dialog");
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        pager = (ViewPager)findViewById(R.id.pagerView);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(pager);
        tabLayout.removeAllTabs();

        tabLayout.addTab(tabLayout.newTab().setText("Artist"), 0);
        tabLayout.addTab(tabLayout.newTab().setText("Shop"), 1);
        tabLayout.addTab(tabLayout.newTab().setText("Board"),2);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.getHeaderView(R.layout.nav_header_main);
        View headerView = navigationView.getHeaderView(0);

        Button btn = (Button)headerView.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn = (Button)headerView.findViewById(R.id.btn_join);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        if (id == R.id.action_settings) {
            return true;
        }

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
        }else if (id == R.id.nav_logout) {

        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuHome = (ImageView)findViewById(R.id.menu_home);


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
