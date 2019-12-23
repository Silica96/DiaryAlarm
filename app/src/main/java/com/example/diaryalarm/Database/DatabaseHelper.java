package com.example.diaryalarm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Display;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "diary_db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Model.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertDiary(String diary){
        SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        // db open 기록 용도로 열기
        SQLiteDatabase db = this.getWritableDatabase();
        // insert 할 데이터를 보관하기 ContentValues
        ContentValues values = new ContentValues();
        values.put(Model.COLUMN_NOTE,diary);
        values.put(Model.COLUMN_DATE,date.format(System.currentTimeMillis()));
        long id = db.insert(Model.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public long insertDiary(String diary, byte[] img){
        SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        // db open 기록 용도로 열기
        SQLiteDatabase db = this.getWritableDatabase();
        // insert 할 데이터를 보관하기 ContentValues
        ContentValues values = new ContentValues();
        values.put(Model.COLUMN_NOTE,diary);
        values.put(Model.COLUMN_DATE,date.format(System.currentTimeMillis()));
        values.put(Model.COLUMN_IMAGE, img);
        long id = db.insert(Model.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // 모든 항목 가져와서 리스트로 보여줄 디비

    public List<Model> getAllNotes() {
        List<Model> diarys = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Model.TABLE_NAME + " ORDER BY " +
                Model.COLUMN_DATE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                Model diary = new Model(
                        cursor.getInt(cursor.getColumnIndex(Model.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Model.COLUMN_NOTE)),
                        cursor.getString(cursor.getColumnIndex(Model.COLUMN_DATE)),
                        cursor.getBlob(cursor.getColumnIndex(Model.COLUMN_IMAGE))
                );
                diarys.add(diary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return diarys;
    }
}
