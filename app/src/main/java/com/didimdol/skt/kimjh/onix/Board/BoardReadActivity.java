package com.didimdol.skt.kimjh.onix.Board;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentData;
import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.CameraDialogFragment;
import com.didimdol.skt.kimjh.onix.R;

import java.util.ArrayList;
import java.util.List;

public class BoardReadActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BoardReadAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText inputView;
    List<BoardCommentData> commentDatas;

    public static final String PARAM_TOTAL_BOARD = "total";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_read);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView onixHome = (ImageView)findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //serializable------------------------------------------------------------------------------------------
        Intent intent = getIntent();
        BoardData data =  (BoardData)intent.getSerializableExtra(PARAM_TOTAL_BOARD);

        //serializable------------------------------------------------------------------------------------------

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new BoardReadAdapter();
        recyclerView.setAdapter(mAdapter);
        inputView = (EditText)findViewById(R.id.edit_message);



        Button btn = (Button)findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputView.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    commentDatas = new ArrayList<BoardCommentData>();
                    commentDatas.add(new BoardCommentData(0,"홍길동", text));
                    mAdapter.addAll(commentDatas);  // commentDatas를 메소드 인자로 보낸다.
                    inputView.setText("");
                    recyclerView.smoothScrollToPosition(mAdapter.getItemCount()-1);
                }
            }
        });
        mAdapter.put(data);
        initData();
    }

    private void initData() {
       /* NetworkManager.getInstance().getBoardReadData(1, new NetworkManager.OnResultListener<List<BoardData>>() {
            @Override
            public void onSuccess(Request request, List<BoardData> result) {
                for (BoardData bd : result) {
                    mAdapter.set(bd);
                }
            }

            @Override
            public void onFailure(Request request,int code, Throwable cause) {

            }
        });*/
    }
}
