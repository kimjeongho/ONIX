package com.didimdol.skt.kimjh.onix.Board;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.didimdol.skt.kimjh.onix.DataClass.BoardWriteResult;
import com.didimdol.skt.kimjh.onix.Manager.NetworkManager;
import com.didimdol.skt.kimjh.onix.R;

import java.io.File;

import okhttp3.Request;

public class BoardWriteActivity extends AppCompatActivity {
    private static final int RC_GALLERY = 1;
    private static final int RC_CAMERA = 2;
    Uri mFileUri;
    ImageView writeOKbtn;
    ImageView btnCamera;
    ImageView btnGallery;
    ImageView boardView;
    EditText titleView;
    EditText contentView;
    Spinner spinnerView;
    int type = -1;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerView = (Spinner) findViewById(R.id.spinner_header);
        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    type = position;
                } else {
                    type = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        titleView = (EditText) findViewById(R.id.edit_title);
        contentView = (EditText) findViewById(R.id.edit_content);

        writeOKbtn = (ImageView) findViewById(R.id.image_insert);
        btnCamera = (ImageView) findViewById(R.id.image_camera);
        boardView = (ImageView) findViewById(R.id.image_board);

        btnGallery = (ImageView) findViewById(R.id.image_gallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RC_GALLERY);
            }
        });


        btnCamera = (ImageView) findViewById(R.id.image_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri());
                startActivityForResult(intent, RC_CAMERA);
            }
        });



        writeOKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type == -1) {
                    Toast.makeText(BoardWriteActivity.this,"말머리를 선택하세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if(file == null){   //file가 null이면 file를 networkmanager에 보내지 않고 통신
                        initData(type,null);
                    }else {
                    initData(type,file);}
//                    finish();
                }
            }
        });

//        boardView.set
       /* initData(type, null);*/


        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable("selected_file");
        }   // file 복원
    }

    private Uri getFileUri() {  //다른 파일 생성 및 저장(카메라 촬영 사진 불러올시 & 이미지 편집시 필요)
        File dir = getExternalFilesDir("myfile");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "my_image_" + System.currentTimeMillis() + ".jpeg");
        mFileUri = Uri.fromFile(file);
        return mFileUri;

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("selected_file", mFileUri);
    }   // 앱 종료시 file 복원

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GALLERY) {    // 갤러리 선택시
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    Uri fileUri = Uri.fromFile(new File(path));
                    file = new File(path);  // file 변수에 File(path) 저장
                    Glide.with(this).load(fileUri).into(boardView);
                    if (fileUri != null) {
                        boardView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        if (requestCode == RC_CAMERA) { //카메라 선택시
            if (resultCode == Activity.RESULT_OK) {
                Glide.with(this).load(mFileUri).into(boardView);
                if (mFileUri != null) {
                    boardView.setVisibility(View.VISIBLE);
                }
                file = new File(mFileUri.getPath());//file 변수에 File(mFileUri.getPath 저장
            }
        }
    }

    private void initData(int type, File file) {
        String title = titleView.getText().toString();
        String content = contentView.getText().toString();
        NetworkManager.getInstance().setBoardWrite(this, type, title, content, file, new NetworkManager.OnResultListener<BoardWriteResult>() {
            @Override
            public void onSuccess(Request request, BoardWriteResult result) {
                if (result.failResult == null) {
                    Toast.makeText(BoardWriteActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                Toast.makeText(BoardWriteActivity.this, "fail: "+request.toString(), Toast.LENGTH_SHORT).show();
            }
        });
       /* NetworkManager.getInstance().setBoardWriteTest(this, type, title, content, new NetworkManager.OnResultListener<BoardWriteResult>() {
            @Override
            public void onSuccess(Request request, BoardWriteResult result) {
                if (result.failResult == null) {
                    Toast.makeText(BoardWriteActivity.this, "success: " + result.successResult.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });*/
    }
}
