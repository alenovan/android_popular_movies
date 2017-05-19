package com.example.alenovan.popularmovies.controller;

/**
 * Created by alenovan on 5/17/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
// TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        String sql = "create table favorite(id integer primary key, id_movies text null, title text null, rate text null,);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        sql = "INSERT INTO favorite (id, id_movies, title, rate) VALUES ('1', 'Vivin Ayu Lestari', '2017-1-30', 'Perempuan');";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
// TODO Auto-generated method stub
    }
}
