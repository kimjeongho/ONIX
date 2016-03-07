package com.didimdol.skt.kimjh.onix.Artist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalData;
import com.didimdol.skt.kimjh.onix.DataClass.ArtistTotalSuccess;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {


    public ArtistFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    ListView listView;
    ArtistAdapter mAdapter;
    Spinner searchSpinner;

    ArtistTotalData artistTotalData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_artist, container, false);



//        View header = inflater.inflate(R.layout.header_search,null);

        listView = (ListView)v.findViewById(R.id.listView);

        mAdapter = new ArtistAdapter();

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArtistTotalData mData = (ArtistTotalData) listView.getItemAtPosition(position);

                Toast.makeText(getContext(), "name: " + mData.artistName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), DetailArtistActivity.class);
                intent.putExtra(DetailArtistActivity.PARAM_TOTAL_ARTIST,mData);
                startActivity(intent);
            }
        });

        searchSpinner = (Spinner)v.findViewById(R.id.spinner_search);
        searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "click: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        initData();

        return v;

    }

    private void initData() {
       /* NetworkManager.getInstance().getArtistData(4, new NetworkManager.OnResultListener<List<ArtistTotalData>>() {
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
        NetworkManager.getInstance().getArtistTotalDataResult(getContext(), 5, "", "", new NetworkManager.OnResultListener<ArtistTotalSuccess>() {
            @Override
            public void onSuccess(Request request, ArtistTotalSuccess result) {
                mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

}
