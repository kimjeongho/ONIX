package com.didimdol.skt.kimjh.onix.Artist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-25.
 */
public class ArtistCommentTitleHolder extends RecyclerView.ViewHolder {
    TextView commentTitleView;

    public ArtistCommentTitleHolder(View itemView) {
        super(itemView);
        commentTitleView = (TextView)itemView.findViewById(R.id.commentTitle);
    }


}
