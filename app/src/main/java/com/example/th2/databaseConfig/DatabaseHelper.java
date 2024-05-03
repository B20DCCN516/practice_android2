package com.example.th2.databaseConfig;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.th2.models.Work;
import com.example.th2.models.WorkStat;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "practice2.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper gI(Context context) {
        if(instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORK_TABLE = "CREATE TABLE IF NOT EXISTS Work (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "dateDone TEXT, " +
                "status TEXT, " +
                "isCongTac INTEGER DEFAULT 0)";
        db.execSQL(CREATE_WORK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Work> getAllWork() {
        List<Work> workList = new ArrayList<>();
        // Câu lệnh SQL để lấy tất cả công việc
        String selectQuery = "SELECT * FROM Work";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {  // Đảm bảo cursor không rỗng và có dữ liệu
            do {
                // Tạo đối tượng Work từ dữ liệu truy vấn
                @SuppressLint("Range") Work work = new Work(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getString(cursor.getColumnIndex("dateDone")),
                        cursor.getString(cursor.getColumnIndex("status")),
                        cursor.getInt(cursor.getColumnIndex("isCongTac")) == 1  // Chuyển đổi integer thành boolean
                );
                workList.add(work);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();  // Đóng cursor để tránh rò rỉ bộ nhớ
        }
        db.close();  // Đóng cơ sở dữ liệu
        return workList;
    }
    public boolean addMusic(Work music) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", music.getName());
        values.put("description", music.getDescription());
        values.put("dateDone", music.getDateDone());
        values.put("status", music.getStatus());
        values.put("isCongTac", music.isCongTac() ? 1 : 0);  // Convert boolean to integer

        // Thêm một bản ghi mới và trả về ID của bản ghi đó

        long id = db.insert("Work", null, values);
        db.close();
        return id != -1;
    }
    public boolean updateMusic(Work music) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", music.getName());
        values.put("description", music.getDescription());
        values.put("dateDone", music.getDateDone());
        values.put("status", music.getStatus());
        values.put("isCongTac", music.isCongTac() ? 1 : 0);  // Convert boolean to integer

        // Cập nhật bản ghi và trả về số hàng bị ảnh hưởng
        int rowsAffected = db.update("Work", values, "id = ?", new String[] { String.valueOf(music.getId()) });
        db.close();  // Đóng cơ sở dữ liệu sau khi thao tác
        return rowsAffected != 0;
    }

    public List<Work> searhByNameOrDes(String searchQuery) {
        List<Work> musicList = new ArrayList<>();
        // Câu lệnh SQL để lấy tất cả bản nhạc
        String selectQuery = "SELECT * FROM Work WHERE name LIKE ? OR description LIKE ? ORDER BY dateDone DESC";
        String[] args = {"%" + searchQuery + "%", "%" + searchQuery + "%"};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, args);

        // Duyệt qua tất cả các hàng và thêm vào danh sách
        while (cursor!= null && cursor.moveToNext()) {
            @SuppressLint("Range")
            Work music = new Work(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("dateDone")),
                    cursor.getString(cursor.getColumnIndex("status")),
                    cursor.getInt(cursor.getColumnIndex("isCongTac")) == 1 ? true : false  // Convert integer to boolean
            );
            musicList.add(music);
        }
        cursor.close();
        db.close();
        return musicList;
    }
    public boolean deleteMusic(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Work", "id = ?", new String[]{String.valueOf(id)}) > 0;
    }


}
