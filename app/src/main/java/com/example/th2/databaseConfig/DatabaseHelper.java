package com.example.th2.databaseConfig;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.th2.models.Music;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "th2.db";
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
        String CREATE_MUSIC_TABLE =
                "CREATE TABLE IF NOT EXISTS Music (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "singer TEXT, " +
                        "album TEXT, " +
                        "type TEXT, " +
                        "isLike INTEGER DEFAULT 0)";
        db.execSQL(CREATE_MUSIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Music> getAllMusic() {
        List<Music> musicList = new ArrayList<>();
        // Câu lệnh SQL để lấy tất cả bản nhạc
        String selectQuery = "SELECT * FROM Music";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Duyệt qua tất cả các hàng và thêm vào danh sách
        while (cursor!= null && cursor.moveToNext()) {
            @SuppressLint("Range")
                    Music music = new Music(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("singer")),
                    cursor.getString(cursor.getColumnIndex("album")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getInt(cursor.getColumnIndex("isLike")) == 1  // Convert integer to boolean
            );
            musicList.add(music);
        }
        cursor.close();
        db.close();
        return musicList;
    }
    public boolean addMusic(Music music) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", music.getName());
        values.put("singer", music.getSinger());
        values.put("album", music.getAlbum());
        values.put("type", music.getType());
        values.put("isLike", music.isLike() ? 1 : 0);  // Convert boolean to integer

        // Thêm một bản ghi mới và trả về ID của bản ghi đó

        long id = db.insert("Music", null, values);
        db.close();
        return id != -1;
    }
    public boolean updateMusic(Music music) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", music.getName());
        values.put("singer", music.getSinger());
        values.put("album", music.getAlbum());
        values.put("type", music.getType());
        values.put("isLike", music.isLike() ? 1 : 0);

        // Cập nhật bản ghi và trả về số hàng bị ảnh hưởng
        int rowsAffected = db.update("Music", values, "id = ?", new String[] { String.valueOf(music.getId()) });
        db.close();  // Đóng cơ sở dữ liệu sau khi thao tác
        return rowsAffected != 0;
    }

    public List<Music> searhByNameOrAlbum(String searchQuery) {
        List<Music> musicList = new ArrayList<>();
        // Câu lệnh SQL để lấy tất cả bản nhạc
        String selectQuery = "SELECT * FROM Music WHERE name LIKE ? OR album LIKE ?";
        String[] args = {"%" + searchQuery + "%", "%" + searchQuery + "%"};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, args);

        // Duyệt qua tất cả các hàng và thêm vào danh sách
        while (cursor!= null && cursor.moveToNext()) {
            @SuppressLint("Range")
            Music music = new Music(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("singer")),
                    cursor.getString(cursor.getColumnIndex("album")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getInt(cursor.getColumnIndex("isLike")) == 1  // Convert integer to boolean
            );
            musicList.add(music);
        }
        cursor.close();
        db.close();
        return musicList;
    }
    public boolean deleteMusic(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Music", "id = ?", new String[]{String.valueOf(id)}) > 0;
    }
}
