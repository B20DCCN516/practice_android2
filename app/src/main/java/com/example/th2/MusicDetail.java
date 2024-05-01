package com.example.th2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.th2.constant.Constant;
import com.example.th2.databaseConfig.DatabaseHelper;
import com.example.th2.models.Music;

public class MusicDetail extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewTitle;
    private EditText editTextName,editTextSinger;
    private Spinner spinAlbum,spinType;
    private Button btnAdd,btnUpdate,btnDelete;
    private String status;
    private CheckBox checkBox;

    private Music currentMusic;

    private DatabaseHelper db;

    private ArrayAdapter<String> albumAdapter, typeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        init();
    }

    public void init() {
        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        textViewTitle = findViewById(R.id.detail_title);
        editTextName = findViewById(R.id.detail_name);
        editTextSinger = findViewById(R.id.detail_singer_name);
        spinAlbum = findViewById(R.id.spin_album);
        spinType = findViewById(R.id.spin_type);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        checkBox = findViewById(R.id.checkIsLike);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        db = DatabaseHelper.gI(this);
        String[] album = {"Cho 1 tình yêu","Nỗi yêu bé dại","Cây lặng- gió ngừng","Có dừng được không","Đây là mơ","Ở giữa cuộc đời"};
        String[] type = {"Country","Blues","Rock","Pop"};

        albumAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,album);
        spinAlbum.setAdapter(albumAdapter);

        typeAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,type);
        spinType.setAdapter(typeAdapter);

        if(status.equals(Constant.ADD)) {
            textViewTitle.setText("Thêm bài hát");
            btnAdd.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        } else {
            btnAdd.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            currentMusic = (Music) intent.getSerializableExtra("music");
            setData();
        }


    }

    public void setData() {
        if (currentMusic == null) {
            return;
        }
        textViewTitle.setText("Bài hát");
        editTextName.setText(currentMusic.getName());
        editTextSinger.setText(currentMusic.getSinger());
        spinAlbum.setSelection(albumAdapter.getPosition(currentMusic.getAlbum()));
        spinType.setSelection(typeAdapter.getPosition(currentMusic.getType()));
        checkBox.setChecked(currentMusic.isLike());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                String name = editTextName.getText().toString();
                String singger = editTextSinger.getText().toString();
                String album = spinAlbum.getSelectedItem().toString();
                String type = spinType.getSelectedItem().toString();
                boolean isLike = checkBox.isChecked();

                if(name.isEmpty()) {
                    editTextName.setError("Vui lòng nhập tên bài hát");
                    break;
                }
                if(singger.isEmpty()) {
                    editTextSinger.setError("Vui lòng nhập tên ca sỹ");
                    break;
                }
                Music music = new Music(name,singger,album,type,isLike);
                if(db.addMusic(music)) {
                    Toast.makeText(this, "Thêm bài hát thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnUpdate:
                name = editTextName.getText().toString();
                singger = editTextSinger.getText().toString();
                album = spinAlbum.getSelectedItem().toString();
                type = spinType.getSelectedItem().toString();
                isLike = checkBox.isChecked();

                if(name.isEmpty()) {
                    editTextName.setError("Vui lòng nhập tên bài hát");
                    break;
                }
                if(singger.isEmpty()) {
                    editTextSinger.setError("Vui lòng nhập tên ca sỹ");
                    break;
                }
                music = new Music(currentMusic.getId(),name,singger,album,type,isLike);
                if(db.updateMusic(music)) {
                    Toast.makeText(this, "Cập nhật bài hát thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDelete:
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thông báo xóa!");
                builder.setTitle("Bạn có chắc muốn xóa bài hát  "+currentMusic.getName()+" không?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteMusic(currentMusic.getId());
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}