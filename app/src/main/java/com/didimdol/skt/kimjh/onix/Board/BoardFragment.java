package com.didimdol.skt.kimjh.onix.Board;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    Spinner searchSpinner;

    public BoardFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    ListView listView;
    BoardAdapter mAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_board, container, false);
        listView = (ListView)v.findViewById(R.id.listView);
        mAdapter = new BoardAdapter();
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardData mData = (BoardData) listView.getItemAtPosition(position);
                Toast.makeText(getContext(), "name: " + mData.boardName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), BoardReadActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btn = (ImageButton)v.findViewById(R.id.btn_write);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getContext(), BoardWriteActivity.class);
                startActivity(intent);
            }
        });


        initData();


        return v;
    }

    private void initData() {
        NetworkManager.getInstance().getBoardData(1, new NetworkManager.OnResultListener<List<BoardData>>() {
            @Override
            public void onSuccess(Request request, List<BoardData> result) {
                for (BoardData bd : result) {
                    mAdapter.add(bd);
                }
            }

            @Override
            public void onFailure(Request request,int code, Throwable cause) {

            }
        });
    }

}
