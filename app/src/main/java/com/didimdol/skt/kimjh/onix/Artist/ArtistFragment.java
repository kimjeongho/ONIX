package com.didimdol.skt.kimjh.onix.Artist;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistListData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistListSuccess;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {

    private Object moreItem;

    public ArtistFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    ListView listView;
    ArtistListAdapter mAdapter;
    Spinner searchSpinner;
    EditText editSearch;
    int type = 1;
    int page = 1;
    boolean isLast = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_artist, container, false);

//        View header = inflater.inflate(R.layout.header_search,null);

        listView = (ListView)v.findViewById(R.id.listView);

        mAdapter = new ArtistListAdapter();

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArtistListData mData = (ArtistListData) listView.getItemAtPosition(position);
//                Toast.makeText(getContext(), "name: " + mData.artistId, LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailArtistActivity.class);
                intent.putExtra(DetailArtistActivity.PARAM_TOTAL_ARTIST, mData.artistId);
                startActivity(intent);
            }
        });

      /*  //Item 확장
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLast && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    getMoreItem();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount - 1){
                    isLast = true;
                } else {
                    isLast = false;
                }
            }
        });*/

        searchSpinner = (Spinner)v.findViewById(R.id.spinner_search);
        searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                    initData(search, 0);
                } else {
                    initData("", type);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        initData("", type);
    }


  /*  boolean isMoreData = false;
    ProgressDialog dialog = null;
    private void getMoreItem() {
        if(isMoreData)return;
        isMoreData = true;
        if(mAdapter.getTotalCount() > 0 && mAdapter.getTotalCount()> mAdapter.getCount()){
            page = mAdapter.getCount() +1;
//            page += 1;
            NetworkManager.getInstance().getArtistListDataResult(getContext(), 2, type*//*condition*//*, "", new NetworkManager.OnResultListener<ArtistListSuccess>() {
                @Override
                public void onSuccess(Request request, ArtistListSuccess result) {
                    Toast.makeText(getContext(), "success", LENGTH_SHORT).show();
                    mAdapter.addAll(result.artistsList);
                    isMoreData = false;
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {
                    isMoreData = false;
                    dialog.dismiss();
                }
            });
            dialog = new ProgressDialog(getContext());
            dialog.setTitle("Loading........");
            dialog.show();

        }
    }*/

    private void initData(String search, int type) {

        NetworkManager.getInstance().getArtistListDataResult(getContext(), page/*page*/, type/*condition*/, search, new NetworkManager.OnResultListener<ArtistListSuccess>() {
            @Override
            public void onSuccess(Request request, ArtistListSuccess result) {
//                Toast.makeText(getContext(), "success", LENGTH_SHORT).show();
                mAdapter.clear(result);
                mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

        /* NetworkManager.getInstance().getArtistData(4, new NetworkManager.OnResultListener<List<ArtistTotalData>>() { //dummyData
            @Override
            public void onSuccess(Request request, List<ArtistTotalData> result) {
                for (ArtistTotalData ad : result) {
                    mAdapter.add(ad);
                }
            }

            @Override
            public void onFailure(Request request,int code, Throwable cause) {

            }
        });*/
    }

}
