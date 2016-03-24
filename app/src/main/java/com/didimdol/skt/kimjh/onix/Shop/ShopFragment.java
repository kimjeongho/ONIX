package com.didimdol.skt.kimjh.onix.Shop;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.ShopListData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ShopListSuccess;
import com.didimdol.skt.kimjh.onix.LocationDialogFragment;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.Manager.PropertyManager;
import com.didimdol.skt.kimjh.onix.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mClient;
    EditText editSearch;
    int type = 1;
    Spinner searchSpinner;
    public ShopFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }
    ShopView shopView;
    ListView listView;
    ShopAdapter mAdapter;

    int page =1;
    boolean isLast = false;

    LocationManager mLM;    // androidLocationManager
    String gpsProvider = LocationManager.GPS_PROVIDER;  // GPS로 검색
    String netProvider = LocationManager.NETWORK_PROVIDER;  //네트워크로 검색

    Location location;
    double userLatitude;
    double userLongitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop, container, false);
        mLM = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);  //android location_service 생성
        listView = (ListView)v.findViewById(R.id.listView);
        mAdapter = new ShopAdapter();
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShopListData mData = (ShopListData) listView.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetailShopActivity.class);
                intent.putExtra(DetailShopActivity.PARAM_TOTAL_SHOP, mData.shopId);
                startActivity(intent);

            }
        });

        //Item 확장
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLast && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int itemCount = mAdapter.getCount();
                    int page = itemCount / 10;
                    page = (itemCount % 10 > 0) ? page + 1 : page;
                    getMoreItem(page+1);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
                    isLast = true;
                } else {
                    isLast = false;
                }
            }
        });


        searchSpinner = (Spinner)v.findViewById(R.id.spinner_search);
        searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "click: " + position, Toast.LENGTH_SHORT).show();
                if(position == 0){
                    type = 1;
                } else if(position == 1){
                    type = 2;
                }
                initData("",type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editSearch = (EditText)v.findViewById(R.id.edit_search);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString();
                if (!TextUtils.isEmpty(search)) {
                    initData(search, 0 );
                } else {
                    initData("", type);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //googlelocation----------------------------------------------------------------------------
        mClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if(savedInstanceState != null){
            isErrorProcessing = savedInstanceState.getBoolean(FIELD_ERROR_PROCESSING);
        }



        return v;
    }

    //리스트뷰 확장
    boolean isMoreData = false;
    ProgressDialog dialog = null;
    private void getMoreItem(int page) {
        if (isMoreData) return;
        isMoreData = true;
        NetworkManager.getInstance().getShopTotalDataResult(getContext(), page, type, "", userLatitude, userLongitude, new NetworkManager.OnResultListener<ShopListSuccess>() {
            @Override
            public void onSuccess(Request request, ShopListSuccess result) {
                mAdapter.addAll(result.shopList);
                isMoreData = false;
//                dialog.dismiss();
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                isMoreData = false;
//                dialog.dismiss();
            }
        });
//        dialog = new ProgressDialog(getContext());
//        dialog.setMessage("Loading........");
//        dialog.show();

    }

    @Override
    public void onStart() {
        super.onStart();
        if(!mLM.isProviderEnabled(gpsProvider) || !mLM.isProviderEnabled(netProvider)){
            if (PropertyManager.getInstance().getCancel() != 777) {
                alertCheckGPS();
            }
        }
        mClient.connect();
    }

    private void alertCheckGPS() {
        LocationDialogFragment f = new LocationDialogFragment();
        f.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData("", type);
    }

    private void initData(String search, int type) {

        NetworkManager.getInstance().getShopTotalDataResult(getContext(), page, type, search, userLatitude, userLongitude, new NetworkManager.OnResultListener<ShopListSuccess>() {
            @Override
            public void onSuccess(Request request, ShopListSuccess result) {
                mAdapter.clear();
                mAdapter.addAll(result.shopList);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

          /*NetworkManager.getInstance().getShopData(4, new NetworkManager.OnResultListener<List<ShopTotalData>>() {
            @Override
            public void onSuccess(Request request, List<ShopTotalData> result) {
                for (ShopTotalData sd : result) {
                    mAdapter.add(sd);
                }
            }

            @Override
            public void onFailure(Request request,int code, Throwable cause) {

            }
        });*/
    }

    @Override
    public void onConnected(Bundle bundle) {
        getLocation();
    }
    private static final int RC_PERMISSION = 1;
    private static final int RC_API_CLIENT = 2;
    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(!ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.ACCESS_FINE_LOCATION) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},RC_PERMISSION);
            }
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mClient);
        if(location != null){
            displayLocation(location);
        }
        LocationRequest request = new LocationRequest();
        request.setFastestInterval(5000);
        request.setInterval(10000);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, request, mListener);
    }

    LocationListener mListener =  new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            displayLocation(location);
        }
    };

    private void displayLocation(Location location) {
//        Toast.makeText(getContext(),location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_SHORT).show();
        userLatitude = location.getLatitude();
        userLongitude = location.getLongitude();
        initData("",type);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_PERMISSION){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (grantResults.length > 0) {
            for (int code : grantResults) {
                if(code == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                    return;
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private static final String FIELD_ERROR_PROCESSING = "errorProcessing";
    boolean isErrorProcessing = false;
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (isErrorProcessing)return;
        isErrorProcessing = true;
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult((Activity) getContext(), RC_API_CLIENT);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
                mClient.connect();
            }
        } else {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog((Activity) getContext(), connectionResult.getErrorCode(),RC_API_CLIENT);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != RC_API_CLIENT) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        isErrorProcessing = false;
        if (resultCode == Activity.RESULT_OK){
            mClient.connect();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FIELD_ERROR_PROCESSING, isErrorProcessing);
    }
}
