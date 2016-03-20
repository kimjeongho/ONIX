package com.didimdol.skt.kimjh.onix.Menu.MenuCustomer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.didimdol.skt.kimjh.onix.R;

public class NoticeActivity extends AppCompatActivity {

    ExpandableListView listView;
    NoticeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

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
