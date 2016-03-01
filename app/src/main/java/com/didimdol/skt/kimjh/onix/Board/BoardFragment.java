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
import android.widget.Toast;

import com.didimdol.skt.kimjh.onix.Artist.ArtistAdapter;
import com.didimdol.skt.kimjh.onix.Artist.DetailArtistActivity;
import com.didimdol.skt.kimjh.onix.DataClass.BoardData;
import com.didimdol.skt.kimjh.onix.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {


    public BoardFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    ListView listView;
    BoardAdapter boardAdapter;
    static final int[] ICON_IDS = {
            R.drawable.onix_board_main_list_data_1,
            R.drawable.onix_board_main_list_data_2,
            R.drawable.onix_board_main_list_data_3,
            R.drawable.onix_board_main_list_data_4,
            R.drawable.onix_board_main_list_data_5,
            R.drawable.onix_board_main_list_data_6,
            R.drawable.onix_board_main_list_data_7
    };

    static final String[] CONTENTS ={
            "이렇게 하는게 맞는 것인가?",
            "이렇게 해도 되는것 같기도하고 아닌것 같기도 하고",
            "맨땅에 헤딩하는 기분",
            "괜찮을까?! 왠지 처음부터 다시 하라고 할 삘 인데.....",
            "이렇게 하는게 되도 문제네.........",
            "알던것 까지 까먹은 듯한 기분",
            "다시 어떻게 회복하지 ㅠㅜㅠㅜ?"
    };


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_board, container, false);
        listView = (ListView)v.findViewById(R.id.listView);
        boardAdapter = new BoardAdapter();
        listView.setAdapter(boardAdapter);

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
        for (int i=0; i<ICON_IDS.length; i++){
            BoardData bd = new BoardData();
            bd.iconid = ICON_IDS[i];
            bd.boardName = "NICKNAME"+i;
            bd.boardCategory = "QnA";
            bd.boardTime = "02월 24일";
            bd.boardTitle = "TITLE" +i;
            bd.boardContent = CONTENTS[i];
            boardAdapter.add(bd);
        }
    }

}
