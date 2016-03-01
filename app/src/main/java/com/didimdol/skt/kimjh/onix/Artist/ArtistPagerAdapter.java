package com.didimdol.skt.kimjh.onix.Artist;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.didimdol.skt.kimjh.onix.DataClass.DetailArtistData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimjh on 2016-02-24.
 */
public class ArtistPagerAdapter extends PagerAdapter {
    List<DetailArtistData> items = new ArrayList<DetailArtistData>();
    List<View> scrappedView = new ArrayList<View>();

    public void add(DetailArtistData item){
        items.add(item);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = null;
        if(scrappedView.size()>0){
            View scrapView = scrappedView.remove(0);
            imageView = (ImageView)scrapView;
        } else {
            imageView = new ImageView(container.getContext());
        }

        imageView.setImageResource(items.get(position).artistPhotos);

        container.addView(imageView);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        container.removeView(view);
        scrappedView.add(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
