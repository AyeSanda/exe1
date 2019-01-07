package com.example.aaung.exe1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDBHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDBHelper = new DatabaseHelper(context);
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void Open(){
        mDatabase = mDBHelper.getWritableDatabase();
    }
    public void Close(){
        mDatabase.close();
    }

}
