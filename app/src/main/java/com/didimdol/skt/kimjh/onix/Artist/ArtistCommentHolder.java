package com.didimdol.skt.kimjh.onix.Artist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-29.
 */
public class ArtistCommentHolder extends RecyclerView.ViewHolder {
    TextView userId;
    TextView userComment;
    ArtistCommentData data;

    public ArtistCommentHolder(View itemView) {
        super(itemView);
        userId = (TextView)itemView.findViewById(R.id.text_user);
        userComment = (TextView)itemView.findViewById(R.id.text_comment);
    }

    public void setArtistComment (ArtistCommentData data){
        this.data = data;
        userId.setText(data.userId);
        userComment.setText(data.userComment);
    }
}
