package com.didimdol.skt.kimjh.onix.Shop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.didimdol.skt.kimjh.onix.DataClass.DetailShopData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * Created by kimjh on 2016-02-27.
 */
public class DetailShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    DetailShopData data;
    public void put(DetailShopData data){
        this.data = data;
        notifyDataSetChanged();
    }

    private static final int VIEW_TYPE_SHOPNAME=0;
    private static final int VIEW_TYPE_ARTIST_LIST = 100;
    private static final int VIEW_TYPE_SHOPPLAYTIME = 200;
//    private static final int VIEW_TYPE_SHOPADDRESS = 300;
    private static final int VIEW_TYPE_SHOPLOCATION = 300;

    @Override
    public int getItemViewType(int position) {
        if(position == 0 ){
            return VIEW_TYPE_SHOPNAME;
        } else if (position > 0 && position < data.artistListDatas.size()+1){
            return VIEW_TYPE_ARTIST_LIST;
        } else if(position==data.artistListDatas.size()+1){
            return VIEW_TYPE_SHOPPLAYTIME;
        } else if(position==(data.artistListDatas.size()+1)+1) {
            return VIEW_TYPE_SHOPLOCATION;
        }/* else if(position==(data.artistListDatas.size()+1)+2){
            return VIEW_TYPE_SHOPLOCATION;
        }*/

        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view ;

        switch (viewType){
            case VIEW_TYPE_SHOPNAME:
                view = inflater.inflate(R.layout.view_ds_shop_title,parent,false);
                return new ShopTitleHolder(view);
            case VIEW_TYPE_ARTIST_LIST:
                view = inflater.inflate(R.layout.view_ds_artistlist, parent, false);
                return new ArtistListHolder(view);
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
        if(position == 0){
            ((ShopTitleHolder)holder).setTitleItem(data);
            return;
        } else
        if(position >0 && position<data.artistListDatas.size()+1){
            ((ArtistListHolder)holder).setArtistListItem(data.artistListDatas.get(position-1));
            //get(position-1) 아티스트 데이터는 0부터 시작이지만 position0 번째가 shopname이 있으므로 -1을 해주어야 아이템이 0번째부터 보일수 있다.
            return;
        } else if(position==data.artistListDatas.size()+1){
            ((ShopTimeHolder)holder).setTimeItme(data.shopTimeDatas.get(position - ((data.artistListDatas.size()) + 1)));
            return;
        }/*else if(position==(data.artistListDatas.size()+1)+1) {
            ((ShopLocationHolder)holder).setMapView(data.shopLocation.get(position-((data.shopTimeDatas.size())+1)));
            return;
        }*/
    }

    @Override
    public int getItemCount() {
        if(data == null) return 0;
        return 2+data.artistListDatas.size()+data.shopTimeDatas.size();
    }
}
