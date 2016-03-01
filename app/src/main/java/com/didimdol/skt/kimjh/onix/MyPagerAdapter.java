package com.didimdol.skt.kimjh.onix;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.didimdol.skt.kimjh.onix.Artist.ArtistFragment;
import com.didimdol.skt.kimjh.onix.Board.BoardFragment;
import com.didimdol.skt.kimjh.onix.Shop.ShopFragment;

/**
 * Created by kimjh on 2016-02-22.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ArtistFragment tab1 = new ArtistFragment();
                return tab1;
            case 1:
                ShopFragment tab2 = new ShopFragment();
                return tab2;
            case 2:
                BoardFragment tab3 = new BoardFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Artist";
            case 1:
                return "Shop";
            case 2:
                return "Board";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
