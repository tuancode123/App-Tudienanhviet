package com.example.dictionary.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dictionary.database.DatabaseHelper;
import com.example.dictionary.model.TuVung;

import java.util.ArrayList;
import java.util.List;

public class TranslateHistoryDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String TABLE_NAME = "HistoryTranslate";
    public static final String SQL_HISTORY_TRANSLATE = "CREATE TABLE " + TABLE_NAME + " (tuTA TEXT PRIMARY KEY, tuTV TEXT, phienAm TEXT DEFAULT '');";

    public static final String TAG = "HoaDonDAO";

    public TranslateHistoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int insertTuVung(TuVung t) {
        ContentValues values = new ContentValues();
        values.put("tuTA", t.getTuTA());
        values.put("tuTV", t.getTuTV());
        values.put("phienAm", t.getPhienAm());
        if (checkPrimaryKey(t.getTuTA())) {
            int result = db.update(TABLE_NAME, values, "tuTA=?", new String[]{t.getTuTA()});
            if (result == 0) {
                return -1;
            }
        } else {
            try {
                if (db.insert(TABLE_NAME, null, values) == 1) {
                    return -1;
                }
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        }
        return 1;
    }

    public TuVung getTuVungByID(String tuTA) {
        TuVung tuVung = null;
        String sSQL = "SELECT * FROM TuVung WHERE tuTA = '" + tuTA + "'";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            tuVung = new TuVung(c.getString(0), c.getString(1));
            c.moveToNext();
        }
        c.close();
        return tuVung;
    }

    public List<TuVung> getAll() {
        List<TuVung> listTu = new ArrayList<>();
        String sSQL = "SELECT * FROM HistoryTranslate";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            TuVung tuVung = new TuVung();
            tuVung.setTuTA(c.getString(0));
            tuVung.setTuTV(c.getString(1));
            tuVung.setPhienAm(c.getString(2));
            listTu.add(tuVung);
            c.moveToNext();
        }
        c.close();
        return listTu;
    }

    public List<TuVung> searchCharacter(String character) {
        List<TuVung> listTu = new ArrayList<>();
        String sSQL = "SELECT * FROM HistoryTranslate WHERE tuTA LIKE '" + character + "%'";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            TuVung tuVung = new TuVung();
            tuVung.setTuTA(c.getString(0));
            tuVung.setTuTV(c.getString(1));
            tuVung.setPhienAm(c.getString(2));
            listTu.add(tuVung);
            c.moveToNext();
        }
        c.close();
        return listTu;
    }

    public void deleteAll() {
        String sSQL = "DELETE FROM HistoryTranslate";
        db.execSQL(sSQL);
    }

    public int updateTuVung(TuVung t) {
        ContentValues values = new ContentValues();
        values.put("tuTA", t.getTuTA());
        values.put("tuTV", t.getTuTV());
        values.put("phienAm", t.getPhienAm());
        int result = db.update(TABLE_NAME, values, "tuTA=?", new String[]{t.getTuTA()});
        return result == 0 ? -1 : 1;
    }

    public int deleteTuvungByID(String tuTA) {
        int result = db.delete(TABLE_NAME, "tuTA=?", new String[]{tuTA});
        return result == 0 ? -1 : 1;
    }

    public boolean checkPrimaryKey(String strPrimaryKey) {
        String[] columns = {"tuTA"};
        String selection = "tuTA=?";
        String[] selectionArgs = {strPrimaryKey};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();
            int i = c.getCount();
            c.close();
            if (i <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
