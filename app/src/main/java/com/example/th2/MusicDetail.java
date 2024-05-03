package com.example.th2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.th2.constant.Constant;
import com.example.th2.databaseConfig.DatabaseHelper;
import com.example.th2.models.Work;

import java.util.Calendar;

public class MusicDetail extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewTitle;
    private EditText editTextName,editTextDescription, editTextDate;
    private Spinner spinStatus;
    private Button btnAdd,btnUpdate,btnDelete;
    private String action;
    private CheckBox checkBox;

    private Work currentMusic;

    private DatabaseHelper db;

    private ArrayAdapter<String> statusAdapte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        init();
    }

    public void init() {
        Intent intent = getIntent();
        action = intent.getStringExtra("status").trim();

        textViewTitle = findViewById(R.id.detail_title);
        editTextName = findViewById(R.id.detail_name);
        editTextDescription = findViewById(R.id.detail_description);
        editTextDate = findViewById(R.id.detail_dateDone);
        spinStatus = findViewById(R.id.spin_status);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        checkBox = findViewById(R.id.checkIsLike);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        editTextDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MusicDetail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String formatdate = String.format("%02d/%02d/%d",day,month+1,year);
                        editTextDate.setText(formatdate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        db = DatabaseHelper.gI(this);


        String[] status = {"Chưa thực hiện","Đang thực hiện","Hoàn thành"};

        statusAdapte = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,status);
        spinStatus.setAdapter(statusAdapte);


        if(action.equals(Constant.ADD)) {
            textViewTitle.setText("Thêm công việc");
            btnAdd.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        } else {
            btnAdd.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            currentMusic = (Work) intent.getSerializableExtra("work");
            setData();
        }
    }

    public void setData() {
        if (currentMusic == null) {
            return;
        }
        textViewTitle.setText("Công việc");
        editTextName.setText(currentMusic.getName());
        editTextDescription.setText(currentMusic.getDescription());
        editTextDate.setText(currentMusic.getDateDone());
        spinStatus.setSelection(statusAdapte.getPosition(currentMusic.getStatus()));
        checkBox.setChecked(currentMusic.isCongTac());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();
                String status = spinStatus.getSelectedItem().toString();
                String date = editTextDate.getText().toString();

                boolean isLike = checkBox.isChecked();
                System.out.println("date: "+date+ " "+description +" "+status+" "+isLike);
                if(name.isEmpty()) {
                    editTextName.setError("Vui lòng nhập tên công việc");
                    break;
                }
                if(description.isEmpty()) {
                    editTextDescription.setError("Vui lòng nhập nội dung công việc");
                    break;
                }
                if(date.isEmpty()) {
                    editTextDate.setError("Vui lòng chọn ngày hoàn thành");
                    break;
                }
                Work work = new Work();
                work.setName(name);
                work.setCongTac(isLike);
                work.setDateDone(date);
                work.setDescription(description);
                work.setStatus(status);
                if(db.addMusic(work)) {
                    Toast.makeText(this, "Thêm công việc thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnUpdate:
                name = editTextName.getText().toString();
                description = editTextDescription.getText().toString();
                status = spinStatus.getSelectedItem().toString();
                date = editTextDate.getText().toString();
                isLike = checkBox.isChecked();

                if(name.isEmpty()) {
                    editTextName.setError("Vui lòng nhập tên công việc");
                    break;
                }
                if(description.isEmpty()) {
                    editTextDescription.setError("Vui lòng nhập nội dung công việc");
                    break;
                }
                if(date.isEmpty()) {
                    editTextDate.setError("Vui lòng chọn ngày hoàn thành");
                    break;
                }
                work = new Work(currentMusic.getId(),name,description,date,status,isLike);
                if(db.updateMusic(work)) {
                    Toast.makeText(this, "Cập nhật công việc thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDelete:
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thông báo xóa!");
                builder.setTitle("Bạn có chắc muốn xóa công việc  "+currentMusic.getName()+" không?");
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