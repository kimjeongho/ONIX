package com.didimdol.skt.kimjh.onix.Board;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-03-01.
 */
public class BoardReadHolder extends RecyclerView.ViewHolder {

    TextView boardTitle;
    ImageView userImage;
    TextView userName;
    TextView userCategory;
    TextView userTime;
    TextView userContent;
    BoardData data;
    public BoardReadHolder(View itemView) {
        super(itemView);
        boardTitle = (TextView)itemView.findViewById(R.id.board_title);
        userImage = (ImageView)itemView.findViewById(R.id.image_user);
        userName = (TextView)itemView.findViewById(R.id.text_user);
        userCategory = (TextView)itemView.findViewById(R.id.text_categoty);
        userTime = (TextView)itemView.findViewById(R.id.text_time);
        userContent = (TextView)itemView.findViewById(R.id.text_content);
    }

    public void setBoardReadItem(BoardData data){
        this.data = data;
        boardTitle.setText(data.boardTitle);
//        userImage.setImageResource(data.iconid);
        if(!TextUtils.isEmpty(data.boardImage))  {
            Glide.with(itemView.getContext())
                    .load(data.boardImage)
                    .into(userImage);
        }
        userName.setText(data.boardName);
        userCategory.setText(data.boardCategory);
        userTime.setText(data.boardTime);
        userContent.setText(data.boardContent);
    }
}
