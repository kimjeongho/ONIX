package com.didimdol.skt.kimjh.onix.Board;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-03-01.
 */
public class BoardCommentHolder extends RecyclerView.ViewHolder {
    TextView userId;
    TextView userComment;
    BoardCommentData data;

    public BoardCommentHolder(View itemView) {
        super(itemView);
        userId = (TextView)itemView.findViewById(R.id.text_user);
        userComment = (TextView)itemView.findViewById(R.id.text_comment);
    }

    public void setBoardComment(BoardCommentData data) {
        this.data = data;
        userId.setText(data.userId);
        userComment.setText(data.userComment);
    }
}
