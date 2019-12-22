package com.example.diaryalarm.Database;

// 여기 참고하기 https://riptutorial.com/ko/android/example/13308/sqlite%EC%97%90-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%A0%80%EC%9E%A5


public class Model {
    public static final String TABLE_NAME = "diary";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_IMAGE = "image";

    private int id;
    private String note;
    private int date;
    private byte[] image;

    public Model(int id, String note, int date, byte[] image) {
        this.id = id;
        this.note = note;
        this.date = date;
        this.image = image;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_DATE + " INTEGER DEFAULT 0,"
                    + COLUMN_IMAGE + " BLOB"
                    + ")";
}
