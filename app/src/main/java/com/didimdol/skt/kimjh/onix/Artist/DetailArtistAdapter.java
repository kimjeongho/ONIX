package com.didimdol.skt.kimjh.onix.Artist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentData;
import com.didimdol.skt.kimjh.onix.DataClass.DetailArtistData;
import com.didimdol.skt.kimjh.onix.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-25.
 */
public class DetailArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//    List<DetailArtistData> items = new ArrayList<DetailArtistData>();
    DetailArtistData data;
    List<ArtistCommentData> items = new ArrayList<ArtistCommentData>();
    public void put(DetailArtistData data){
//        items.add(data);
        this.data = data;
        notifyDataSetChanged();
    }

    public void addAll(List<ArtistCommentData> datas){  // Activity에서 commentDatas를 받는다 (같은 List형태)
//        items.addAll(datas);
        data.artistComment.addAll(datas);
        notifyDataSetChanged();
    }

    private static final int VIEW_TYPE_DETAIL_ARTIST = 0;
    private static final int VIEW_TYPE_NAILTYPE = 100;
    private static final int VIEW_TYPE_COMMENTTITLE = 200;
    private static final int VIEW_TYPE_COMMENT = 300;
    private static final int VIEW_TYPE_MORE = 400;

    @Override
    public int getItemViewType(int position) {
//        DetailArtistData data = items.get(position);
        if(position == 0){  //position 0 아티스트 프로필 이미지 보기
            return VIEW_TYPE_DETAIL_ARTIST;
        } else if(position > 0 && position < data.nailType.size() + 1 ){    // 네일 타입 페이지
            return VIEW_TYPE_NAILTYPE;
        } else if(position == data.nailType.size()+1){  //댓글 타이틀
            return VIEW_TYPE_COMMENTTITLE;
        } else if(position>data.nailType.size()+1 && position< (data.nailType.size()+1)+1+data.artistComment.size()+1){//코멘트
            return VIEW_TYPE_COMMENT;
        } else if(position == (data.nailType.size()+1)+1+data.artistComment.size()+1){
            return VIEW_TYPE_MORE;
        }
        return super.getItemViewType(position); //??
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view ;

        switch (viewType){
            case VIEW_TYPE_DETAIL_ARTIST:
                view = inflater.inflate(R.layout.view_da_artistprofile, parent, false);
                return new ArtistProfileHolder(view);
            case VIEW_TYPE_NAILTYPE:
                view = inflater.inflate(R.layout.view_da_nailtype, parent, false);
                return new NailTypeHolder(view);
            case VIEW_TYPE_COMMENTTITLE:
                view = inflater.inflate(R.layout.view_da_commenttitle, parent, false);
                return new ArtistCommentTitleHolder(view);
            case VIEW_TYPE_COMMENT:
                view = inflater.inflate(R.layout.view_da_br_comment, parent, false);
                return new ArtistCommentHolder(view);
            case VIEW_TYPE_MORE:
                view = inflater.inflate(R.layout.view_da_more, parent, false);
                return new MoreHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            ((ArtistProfileHolder)holder).setArtistProfileItem(data);
            return;
        } else if(position > 0 && position < data.nailType.size() + 1 ){
            ((NailTypeHolder)holder).setNailType(data.nailType.get(position-1));
            return;
        }
        /*else if(position == data.nailType.size()+1){

            return;}*/
        else if(position>data.nailType.size()+1 && position< (data.nailType.size()+1)+1+data.artistComment.size()+1){
            ((ArtistCommentHolder)holder).setArtistComment(data.artistComment.get(position-((data.nailType.size())+2)));
            return;
        } /*else if(position == (data.nailType.size()+1)+1+data.comment.size()+1){
            return;
        }*/
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0; //???
        return 2+data.nailType.size()+data.artistComment.size();
    }
}
