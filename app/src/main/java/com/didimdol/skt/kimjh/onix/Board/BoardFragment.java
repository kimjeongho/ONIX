package com.didimdol.skt.kimjh.onix.Board;


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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.DataClass.BoardTotalSuccess;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import okhttp3.Request;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
    public BoardFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    ListView listView;
    BoardAdapter mAdapter;
    Spinner categorySpinner;
    EditText editSearch;
    int type = 1;

    int page =1;
    boolean isLast = false;
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
                Intent intent = new Intent(getContext(), BoardReadActivity.class);
                intent.putExtra(BoardReadActivity.EXTRA_BOARD_TYPE, type);
                intent.putExtra(BoardReadActivity.PARAM_TOTAL_BOARD, mData);
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


        categorySpinner = (Spinner)v.findViewById(R.id.spinner_category);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    initData(search, type);
                } else {
                    initData("", type);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ImageView btn = (ImageView)v.findViewById(R.id.btn_write);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BoardWriteActivity.class);
                startActivity(intent);
            }
        });


        return v;
    }
    boolean isMoreData = false;
//    ProgressDialog dialog = null;
    private void getMoreItem(int page) {
        if (isMoreData) return;
        isMoreData = true;
        NetworkManager.getInstance().getBoardTotalDataResult(getContext(),type, page/*page*/,  "", new NetworkManager.OnResultListener<BoardTotalSuccess>() {
            @Override
            public void onSuccess(Request request, BoardTotalSuccess result) {
//                mAdapter.clear();
                mAdapter.set(result);
                isMoreData = false;
//                dialog.dismiss();
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(getContext(),"fail",LENGTH_SHORT).show();
                isMoreData = false;
//                dialog.dismiss();
            }
        });
//        dialog = new ProgressDialog(getContext());
//        dialog.setMessage("Loading........");
//        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData("", type);
    }

    private void initData(String search, int type) {
        NetworkManager.getInstance().getBoardTotalDataResult(getContext(),type, page/*page*/,  search, new NetworkManager.OnResultListener<BoardTotalSuccess>() {
            @Override
            public void onSuccess(Request request, BoardTotalSuccess result) {
                mAdapter.clear();
                mAdapter.set(result);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(getContext(),"fail",LENGTH_SHORT).show();
            }
        });

           /*NetworkManager.getInstance().getBoardData(1, new NetworkManager.OnResultListener<List<BoardData>>() {
            @Override
            public void onSuccess(Request request, List<BoardData> result) {
                for (BoardData bd : result) {
                    mAdapter.add(bd);
                }
            }

            @Override
            public void onFailure(Request request,int code, Throwable cause) {

            }
        });*/
    }

}
