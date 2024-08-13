package com.econetwork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "econetworkDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // создаем таблицу с полями
        db.execSQL("create table econetworktable ("
                + "id,"
                + "token,"
                + "name,"
                + "pass" + ");");

        db.execSQL("create table econetworknotif ("
                + "type,"
                + "id,"
                + "title,"
                + "city,"
                + "created_at,"
                + "discription" + ");");

        db.execSQL("create table econetworkchat ("
                + "id,"
                + "name,"
                + "date,"
                + "their_text,"
                + "my_text" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}