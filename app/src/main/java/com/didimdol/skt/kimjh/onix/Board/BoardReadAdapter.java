package com.didimdol.skt.kimjh.onix.Board;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didimdol.skt.kimjh.onix.DataClass.BoardCommentData;
import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-25.
 */
public class BoardReadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//    List<DetailArtistData> items = new ArrayList<DetailArtistData>();
    BoardData data;
    List<BoardCommentData> items = new ArrayList<BoardCommentData>();
    public void put(BoardData data){
//        items.add(data);
        this.data = data;
        notifyDataSetChanged();
    }

    public void addAll(List<BoardCommentData> datas){  // Activity에서 commentDatas를 받는다 (같은 List형태)
//        items.addAll(datas);
        data.boardCommentDatas.addAll(datas);
        notifyDataSetChanged();
    }

    private static final int VIEW_TYPE_BOARD_READ = 0;
    private static final int VIEW_TYPE_BOARD_COMMENT = 100;
//    private static final int VIEW_TYPE_MORE = 400;

    @Override
    public int getItemViewType(int position) {
//        DetailArtistData data = items.get(position);
        if(position == 0){  //position 0 아티스트 프로필 이미지 보기
            return VIEW_TYPE_BOARD_READ;
        } else if(position > 0 && position < data.boardCommentDatas.size() + 1 ){    // 네일 타입 페이지
            return VIEW_TYPE_BOARD_COMMENT;
        }/* else if(position == data.nailType.size()+1){  //댓글 타이틀
            return VIEW_TYPE_COMMENTTITLE;
        } else if(position>data.nailType.size()+1 && position< (data.nailType.size()+1)+1+data.artistComment.size()+1){//코멘트
            return ;
        } else if(position == (data.nailType.size()+1)+1+data.artistComment.size()+1){
            return VIEW_TYPE_MORE;
        }*/
        return super.getItemViewType(position); //??
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view ;

        switch (viewType){
            case VIEW_TYPE_BOARD_READ:
                view = inflater.inflate(R.layout.view_br_content, parent, false);
                return new BoardReadHolder(view);
            case VIEW_TYPE_BOARD_COMMENT:
                view = inflater.inflate(R.layout.view_da_br_comment, parent, false);
                return new BoardCommentHolder(view);
           /* case VIEW_TYPE_COMMENTTITLE:
                view = inflater.inflate(R.layout.view_da_commenttitle, parent, false);
                return new ArtistCommentTitleHolder(view);

            case VIEW_TYPE_MORE:
                view = inflater.inflate(R.layout.view_da_more, parent, false);
                return new MoreHolder(view);*/

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            ((BoardReadHolder)holder).setBoardReadItem(data);
            return;
        } else if(position > 0 && position < data.boardCommentDatas.size() + 1 ){
            ((BoardCommentHolder)holder).setBoardComment(data.boardCommentDatas.get(position - 1));
            return;
        }
        /*else if(position == data.nailType.size()+1){

            return;}*/
       /* else if(position>data.nailType.size()+1 && position< (data.nailType.size()+1)+1+data.artistComment.size()+1){
            ((ArtistCommentHolder)holder).setArtistComment(data.artistComment.get(position-((data.nailType.size())+2)));
            return;
        } *//*else if(position == (data.nailType.size()+1)+1+data.comment.size()+1){
            return;
        }*/
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0; //???
        return 1+data.boardCommentDatas.size();
    }
}
