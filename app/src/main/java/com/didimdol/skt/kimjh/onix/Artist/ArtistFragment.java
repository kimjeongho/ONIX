package com.didimdol.skt.kimjh.onix.Artist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import okhttp3.Request;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {

    public ArtistFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    ListView listView;
    ArtistListAdapter mAdapter;
    Spinner searchSpinner;
    EditText editSearch;
    String setItem;

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
                Toast.makeText(getContext(), "name: " + mData.artistId, LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailArtistActivity.class);
                intent.putExtra(DetailArtistActivity.PARAM_TOTAL_ARTIST, mData.artistId);
                startActivity(intent);
            }
        });

        searchSpinner = (Spinner)v.findViewById(R.id.spinner_search);
        /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.textview_with_background,getResources().getStringArray(R.array.search) );

        spinnerArrayAdapter.setDropDownViewResource(R.layout.textview_with_background);
        searchSpinner.setAdapter(spinnerArrayAdapter);*/
        searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setItem = (String) searchSpinner.getSelectedItem();
                Toast.makeText(getContext(), "click: " + setItem, LENGTH_SHORT).show();
                int type = position;
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
                initData(search,0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        initData("",0);

        return v;

    }

    private void initData(String search, int type) {
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
        NetworkManager.getInstance().getArtistListDataResult(getContext(), 1, type, search, new NetworkManager.OnResultListener<ArtistListSuccess>() {
            @Override
            public void onSuccess(Request request, ArtistListSuccess result) {
                Toast.makeText(getContext(),"success",LENGTH_SHORT).show();
                mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

}
