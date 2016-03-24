package com.didimdol.skt.kimjh.onix.Board;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentReadSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentData;
import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentReadSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentResult;
import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.MainActivity;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import okhttp3.Request;

public class BoardReadActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BoardReadAdapter mAdapter;
    LinearLayoutManager layoutManager;
    EditText inputView;
    //댓글 확장시 사용
    int page =1;
    boolean isLast = false;
    BoardData data;

    public static final String PARAM_TOTAL_BOARD = "total";
    public static final String EXTRA_BOARD_TYPE = "boardType";

    private int boardType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_read);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView onixHome = (ImageView) findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(BoardReadActivity.this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();
            }
        });
        //serializable------------------------------------------------------------------------------------------
        Intent intent = getIntent();
        boardType = intent.getIntExtra(EXTRA_BOARD_TYPE, 0);
        data = (BoardData) intent.getSerializableExtra(PARAM_TOTAL_BOARD);
        //serializable------------------------------------------------------------------------------------------

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new BoardReadAdapter();
        recyclerView.setAdapter(mAdapter);
        inputView = (EditText) findViewById(R.id.edit_message);

        //recyclerview 확장---------------------------------------------------------------------------------------------------------------------
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLast && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemCount = mAdapter.items.size();
                    int page = itemCount/10;
                    page = (itemCount % 10 > 0)? page+1:page;
                    getMoreItem(page+1);
                    Toast.makeText(BoardReadActivity.this,"scroll",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (totalItemCount > 0 && lastVisibleItemPosition != RecyclerView.NO_POSITION &&
                        (totalItemCount - 1 <= lastVisibleItemPosition)){
                    isLast = true;
                } else {
                    isLast = false;
                }
            }
        });
        //recyclerview 확장---------------------------------------------------------------------------------------------------------------------


        Button btn = (Button) findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCommentData();
            }
        });
        mAdapter.put(data);
        /*initData();*/
    }

    //댓글 더보기
    boolean isMoreData = false;
    ProgressDialog dialog = null;
    private void getMoreItem(int page) {
        if(isMoreData)return;
        isMoreData = true;
        NetworkManager.getInstance().getBoardCommentDataResult(this, boardType, data.postId, page, new NetworkManager.OnResultListener<BoardCommentReadSuccess>() {
            @Override
            public void onSuccess(Request request, BoardCommentReadSuccess result) {
//                mAdapter.clear();
                mAdapter.set(result);
                isMoreData = false;
                dialog.dismiss();
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(BoardReadActivity.this, "" + cause, Toast.LENGTH_SHORT).show();
                isMoreData = false;
                dialog.dismiss();
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading........");
        dialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
       initData();
    }

    private void initData() {   // 게시글 댓글 보기
        NetworkManager.getInstance().getBoardCommentDataResult(this,boardType,data.postId, page, new NetworkManager.OnResultListener<BoardCommentReadSuccess>() {
            @Override
            public void onSuccess(Request request, BoardCommentReadSuccess result) {
                mAdapter.clear();
                mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

    private void initCommentData() {    //댓글 작성
        String commentText = inputView.getText().toString();
        NetworkManager.getInstance().setBoardCommentResult(this, boardType, data.postId, commentText, new NetworkManager.OnResultListener<BoardCommentResult>() {
            @Override
            public void onSuccess(Request request, BoardCommentResult result) {
                if (result.failResult == null) {
//                    Toast.makeText(BoardReadActivity.this, "댓글 작성 성공 ", Toast.LENGTH_SHORT).show();
                    initData();
                    inputView.setText("");
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(BoardReadActivity.this, "fail ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
