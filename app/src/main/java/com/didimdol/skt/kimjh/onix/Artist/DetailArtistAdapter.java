package com.didimdol.skt.kimjh.onix.Artist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistCommentReadSuccess;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.OnArtistItemClickListener;
import com.didimdol.skt.kimjh.onix.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-25.
 */
public class DetailArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnArtistItemClickListener {
//    List<DetailArtistData> items = new ArrayList<DetailArtistData>();
//    DetailArtistData data;
    ArtistTotalData data;
//    List<ArtistCommentData> items = new ArrayList<ArtistCommentData>();
   /* public void set(DetailArtistData data){
//        items.add(data);
        this.data = data;
        notifyDataSetChanged();
    } */
    public void set(ArtistTotalData data){
//        items.add(data);
        this.data = data;
        notifyDataSetChanged();
    }

    //더보기
    public void add(ArtistCommentReadSuccess items){
        data.artistComment.addAll(items.comments);
        notifyDataSetChanged();
    }




   /* public void addAll(List<ArtistCommentData> datas){  // Activity에서 commentDatas를 받는다 (같은 List형태)
//        items.addAll(datas);
        data.artistComment.addAll(datas);
        notifyDataSetChanged();
    }*/

    OnArtistItemClickListener itemClickListener;

    /*@Override
    public void onItemClick(View view, int position) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(view, position);
        }
    }*/

    public void setOnItemClickListener(OnArtistItemClickListener listener){
        itemClickListener = listener;
    }

    @Override
    public void onShopClick(View view, ArtistTotalData artistTotalData) {
        if(itemClickListener != null){
            itemClickListener.onShopClick(view, artistTotalData);
        }
    }

    @Override
    public void onChoiceClick(View view, ArtistTotalData artistTotalData) {
        if(itemClickListener != null){
            itemClickListener.onChoiceClick(view, artistTotalData);
        }
    }



    private static final int VIEW_TYPE_ARTIST_PHOTOS = 0;
    private static final int VIEW_TYPE_DETAIL_ARTIST = 100;
    private static final int VIEW_TYPE_NAILTYPE = 200;
    private static final int VIEW_TYPE_COMMENTTITLE = 300;
    private static final int VIEW_TYPE_COMMENT = 400;
    private static final int VIEW_TYPE_MORE = 500;

    @Override
    public int getItemViewType(int position) {
//        DetailArtistData data = items.get(position);
        if(position == 0){  //position 0 아티스트 프로필 이미지 보기
            return VIEW_TYPE_ARTIST_PHOTOS;
        } else if(position == 1){  //position 0 아티스트 프로필 이미지 보기
            return VIEW_TYPE_DETAIL_ARTIST;
        }else if(position > 1 && position < data.nailType.size() + 2){    // 네일 타입 페이지
            return VIEW_TYPE_NAILTYPE;
        } else if(position == data.nailType.size()+2){  //댓글 타이틀
            return VIEW_TYPE_COMMENTTITLE;
        } else if(position>data.nailType.size()+2 && position< (data.nailType.size()+1)+2+data.artistComment.size()+1){//코멘트
            return VIEW_TYPE_COMMENT;
        } /*else if(position == (data.nailType.size()+1)+2+data.artistComment.size()+1){
            return VIEW_TYPE_MORE;
        }*/
        return super.getItemViewType(position); //??
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view ;

        switch (viewType){
            case VIEW_TYPE_ARTIST_PHOTOS:
                view = inflater.inflate(R.layout.view_da_artistphotos , parent , false);
                ArtistPhotosHolder holder = new ArtistPhotosHolder(view);
                holder.setOnArtistItemClickListener(this);
                return  /*new ArtistPhotosHolder(view)*/ holder;
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
            ((ArtistPhotosHolder)holder).setArtistPhotosItem(data);
            return;
        }else if(position == 1){
            ((ArtistProfileHolder)holder).setArtistProfileItem(data);
            return;
        } else if(position > 1 && position < data.nailType.size() + 2 ){
            ((NailTypeHolder)holder).setNailType(data.nailType.get(position-2));
            return;
        }
        /*else if(position == data.nailType.size()+1){

            return;}*/
        else if(position>data.nailType.size()+2 && position< (data.nailType.size()+1)+2+data.artistComment.size()+1){
            ((ArtistCommentHolder)holder).setArtistComment(data.artistComment.get(position-((data.nailType.size())+3)));
            return;
        } /*else if(position == (data.nailType.size()+1)+1+data.comment.size()+1){
            return;
        }*/
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0; //???
        return 3+data.nailType.size()+data.artistComment.size();
    }



}
