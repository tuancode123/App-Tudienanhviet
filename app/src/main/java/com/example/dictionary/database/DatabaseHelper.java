package com.example.dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dictionary.dao.TranslateHistoryDAO;
import com.example.dictionary.dao.TuVungDAO;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbDictionary";
    public static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TuVungDAO.SQL_TU_VUNG);
        db.execSQL(TranslateHistoryDAO.SQL_HISTORY_TRANSLATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TuVungDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + TranslateHistoryDAO.TABLE_NAME);
        onCreate(db);
    }
}
