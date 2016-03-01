package com.didimdol.skt.kimjh.onix;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * Created by dongja94 on 2016-01-28.
 */
public class MyDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        /*builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Dialog");*/
        builder.setMessage("'ONIX'에서 현재 위치 정보를 사용하고자 합니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Yes Click", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "No Click", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setCancelable(false);
        return builder.create();
    }
}
