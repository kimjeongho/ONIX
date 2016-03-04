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

import com.didimdol.skt.kimjh.onix.DataClass.ArtistData;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import java.util.List;

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
                ArtistData mData = (ArtistData) listView.getItemAtPosition(position);

                Toast.makeText(getContext(), "name: " + mData.artistName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailArtistActivity.class);
                startActivity(intent);
            }
        });

        Spinner searchSpinner = (Spinner)v.findViewById(R.id.spinner_search);
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
        NetworkManager.getInstance().getArtistData(4, new NetworkManager.OnResultListener<List<ArtistData>>() {
            @Override
            public void onSuccess(List<ArtistData> result) {
                for (ArtistData ad : result) {
                    mAdapter.add(ad);
                }
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }

}
