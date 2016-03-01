package com.didimdol.skt.kimjh.onix.Menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.Artist.ArtistView;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopData;
import com.didimdol.skt.kimjh.onix.R;

public class ChoiceActivity extends AppCompatActivity {
    ChoiceAdapter mAdapter;
    ListView listView;
    static final int[] ICON_IDS = {
            R.drawable.dummy_1,
            R.drawable.dummy_2,
            R.drawable.dummy_3,
            R.drawable.dummy_4
    };

    static final int[] ICON_IDS_SHOP ={
            R.drawable.onix_shop_main_list_data_1,
            R.drawable.onix_shop_main_list_data_2,
            R.drawable.onix_shop_main_list_data_3,
            R.drawable.onix_shop_main_list_data_4
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        listView = (ListView)findViewById(R.id.listView_choice);
        mAdapter = new ChoiceAdapter();
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if (position == mAdapter.getItemViewType(0)){
                    Toast.makeText(ChoiceActivity.this,"choiceArtist",Toast.LENGTH_SHORT).show();
                } else if (position> mAdapter.getItemViewType(1)){
                    Toast.makeText(ChoiceActivity.this,"choiceShop",Toast.LENGTH_SHORT).show();
                }

*/
            }
        });

        initArtistData();
        initShopData();
    }

    private void initShopData() {
        for (int i=0; i<ICON_IDS_SHOP.length; i++) {
            ShopData sd = new ShopData();
            sd.shopName = "NICK NAME"+i;
            sd.iconid = ICON_IDS_SHOP[i];
            sd.shopChoice = "Choice"+i;
            sd.location = "3km";
            mAdapter.add(sd);
        }
    }

    private void initArtistData() {
        for (int i=0; i<ICON_IDS.length; i++)
        {
            ArtistData ad = new ArtistData();
            ad.artistName = "NICK NAME"+i;
            ad.iconid = ICON_IDS[i];
            ad.artistChoice = "Choice"+i;
            ad.location = "3km";
            ad.choiceId=R.drawable.onix_sale;
            mAdapter.add(ad);

        }



    }
}
