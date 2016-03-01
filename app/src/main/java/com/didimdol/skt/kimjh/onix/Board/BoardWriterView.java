package com.didimdol.skt.kimjh.onix.Board;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;

/**
 * Created by kimjh on 2016-02-23.
 */
public class BoardWriterView extends FrameLayout {
    public BoardWriterView(Context context) {
        super(context);
        init();
    }

    ImageView boardView;
    TextView boardNameView, boardCategoryView, boardContentView,boardTitleView;
    RelativeTimeTextView boardTimeView;
    BoardData boardData;


    public void init() {
        inflate(getContext(), R.layout.item_board,this);
        boardView = (ImageView)findViewById(R.id.image_board);
        boardNameView = (TextView)findViewById(R.id.text_id);
        boardCategoryView = (TextView)findViewById(R.id.text_cate);
        boardTimeView = (RelativeTimeTextView)findViewById(R.id.text_time);
        boardTitleView = (TextView)findViewById(R.id.text_title);
        boardContentView = (TextView)findViewById(R.id.text_content);
    }

    public void setBoardWriteItem(BoardData boardData) {
        this.boardData = boardData;
        boardView.setImageResource(boardData.iconid);
        boardNameView.setText(boardData.boardName);
        boardCategoryView.setText(boardData.boardCategory);
        boardTimeView.setText(boardData.boardTime);
        boardTitleView.setText(boardData.boardTitle);
        boardContentView.setText(boardData.boardContent);
    }
}
