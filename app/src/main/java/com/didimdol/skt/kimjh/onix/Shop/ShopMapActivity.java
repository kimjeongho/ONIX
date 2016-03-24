package com.didimdol.skt.kimjh.onix.Shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.MainActivity;
import com.didimdol.skt.kimjh.onix.R;
import com.skp.Tmap.TMapView;

public class ShopMapActivity extends AppCompatActivity {
    TMapView mapView;
    ShopTotalData mData;
    private static final String API_KEY = "ac7a89e6-e0b6-37d1-af6e-399c46a03753";
    public static final String PARAM_LOCATION = "location";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView onixHome = (ImageView) findViewById(R.id.onix_home);
        onixHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(ShopMapActivity.this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();
            }
        });

        //serializable------------------------------------------------------------------------------------------
        Intent intent = getIntent();
        mData = (ShopTotalData) intent.getSerializableExtra(PARAM_LOCATION);
        //serializable------------------------------------------------------------------------------------------

        Toast.makeText(ShopMapActivity.this, mData.latitude+","+mData.longitude,Toast.LENGTH_SHORT).show();

        mapView = (TMapView)findViewById(R.id.shop_map);
        mapView.setOnApiKeyListener(new TMapView.OnApiKeyListenerCallback() {
            @Override
            public void SKPMapApikeySucceed() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUpMap();
                    }
                });
            }

            @Override
            public void SKPMapApikeyFailed(String s) {

            }
        });
        mapView.setSKPMapApiKey(API_KEY);
        double latitude = Double.parseDouble(mData.latitude);
        double longitude = Double.parseDouble(mData.longitude);
        mapView.setLocationPoint(longitude,latitude);
        mapView.setCenterPoint(longitude,latitude);

        mapView.setIconVisibility(true);
        Bitmap bm = ((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.markericon)).getBitmap();
        mapView.setIcon(bm);
        mapView.setZoomLevel(17);

        ImageView btn = (ImageView)findViewById(R.id.image_expand);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.MapZoomIn();
            }
        });

        btn=(ImageView)findViewById(R.id.image_redus);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.MapZoomOut();
            }
        });
    }

    private void setUpMap() {
       /* double latitude = Double.parseDouble(mData.latitude);
        double longitude = Double.parseDouble(mData.longitude);
        mapView.setLocationPoint(longitude,latitude);
        mapView.setCenterPoint(longitude,latitude);

        mapView.setLocationPoint(126.943384, 37.496789);
        mapView.setCenterPoint(126.943384, 37.496789);

        mapView.setIconVisibility(true);
        Bitmap bm = ((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.onix_icon_48)).getBitmap();
        mapView.setIcon(bm);
*/

    }
}
