package com.didimdol.skt.kimjh.onix.Menu.MenuCustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.didimdol.skt.kimjh.onix.MainActivity;
import com.didimdol.skt.kimjh.onix.R;

public class NoticeActivity extends AppCompatActivity {

    ExpandableListView listView;
    NoticeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ImageView onixHome = (ImageView) findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(NoticeActivity.this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();
            }
        });
        listView = (ExpandableListView)findViewById(R.id.expandableListView);

        mAdapter = new NoticeAdapter();
        listView.setAdapter(mAdapter);

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        initData();

        for (int i=0; i<mAdapter.getGroupCount(); i++){
            listView.expandGroup(i);
        }
    }

    private void initData() {
        for (int i = 0; i<2; i++){
            for (int j = 0; j<1; j++){
                    mAdapter.put("dd"+i,"22"+j);

            }
        }
    }
}
