package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.DetailShopData;
import com.didimdol.skt.kimjh.onix.OnShopItemClickListener;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-27.
 */
public class DetailShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnShopItemClickListener {
    DetailShopData data;

    public void put(DetailShopData data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private static final int VIEW_DETAIL_SHOP_PHOTOS = 0;
    private static final int VIEW_TYPE_SHOPNAME = 100;
    private static final int VIEW_TYPE_ARTIST_LIST = 200;
    private static final int VIEW_TYPE_SHOPPLAYTIME = 300;
    private static final int VIEW_TYPE_SHOPLOCATION = 400;
    //-----------onClickListener----------------------------------------------------------------------------------------
    OnShopItemClickListener itemClickListener;

    public void setOnShopItemClickListener(OnShopItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public void onCallClick(View view, int position) {
        if(itemClickListener != null){
            itemClickListener.onCallClick(view, position);
        }
    }

    @Override
    public void onChoiceClick(View view, int position) {
        if(itemClickListener != null){
            itemClickListener.onChoiceClick(view, position);
        }
    }

    @Override
    public void onArtistListClick(View view, int position) {
        if(itemClickListener != null){
            itemClickListener.onArtistListClick(view, position);
        }
    }



    //---------------------------------------------------------------------------------------------------
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_DETAIL_SHOP_PHOTOS;
        } else if (position == 1) {
            return VIEW_TYPE_SHOPNAME;
        } else if (position > 1 && position < data.artistListDatas.size() + 2) {
            return VIEW_TYPE_ARTIST_LIST;
        } else if (position == data.artistListDatas.size() + 2) {
            return VIEW_TYPE_SHOPPLAYTIME;
        } else if (position == (data.artistListDatas.size() + 1) + 2) {
            return VIEW_TYPE_SHOPLOCATION;
        }/* else if(position==(data.artistListDatas.size()+1)+2){
            return VIEW_TYPE_SHOPLOCATION;
        }*/

        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {

            case VIEW_DETAIL_SHOP_PHOTOS:
                view = inflater.inflate(R.layout.view_ds_shopphotos, parent, false);
                ShopPhotosHolder holderPhotos = new ShopPhotosHolder(view);
                holderPhotos.setOnShopItemClickListener(this);
                return holderPhotos;
            case VIEW_TYPE_SHOPNAME:
                view = inflater.inflate(R.layout.view_ds_shop_title, parent, false);
                return new ShopTitleHolder(view);
            case VIEW_TYPE_ARTIST_LIST:
                view = inflater.inflate(R.layout.view_ds_artistlist, parent, false);
                ArtistListHolder holder = new ArtistListHolder(view);
                holder.setOnShopItemClickListener(this);
                return holder;
            case VIEW_TYPE_SHOPPLAYTIME:
                view = inflater.inflate(R.layout.view_ds_shop_playtime, parent, false);
                return new ShopTimeHolder(view);
            case VIEW_TYPE_SHOPLOCATION:
                view = inflater.inflate(R.layout.view_ds_map, parent, false);
                return new ShopLocationHolder(view);
           /* case VIEW_TYPE_SHOPLOCATION:
                view = inflater.inflate(R.layout.view_ds_map, parent, false);
                return new ShopLocationHolder(view);*/
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((ShopPhotosHolder) holder).setShopPhotosItem(data);
        } else if (position == 1) {
            ((ShopTitleHolder) holder).setTitleItem(data);
            return;
        } else if (position > 1 && position < data.artistListDatas.size() + 2) {
            ((ArtistListHolder) holder).setArtistListItem(data.artistListDatas.get(position - 2));
            //get(position-1) 아티스트 데이터는 0부터 시작이지만 position0 번째가 shopname이 있으므로 -1을 해주어야 아이템이 0번째부터 보일수 있다.
            return;
        } else if (position == data.artistListDatas.size() + 2) {
            ((ShopTimeHolder) holder).setTimeItme(data.shopTimeDatas);
            return;
        }else {
            ((ShopLocationHolder)holder).setMapView(data.shopLocation);
            return;
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return 4 + data.artistListDatas.size()/*+data.shopTimeDatas.size()+data.shopLocation.size()*/;
    }


}
