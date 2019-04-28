package com.example.vladislavsvasiljevs.pchub.DatabaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperSSD extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelperCPU";

    private static final String TABLE_NAME = "ssd_temp";
    private static final String COL1 = "ID";
    private static final String COL2 = "DATE";
    private static final String COL3 = "SSD_Temp_Value";


    public DatabaseHelperSSD(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " +
                TABLE_NAME +
                "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + "," +
                COL3 + "" +
                ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addSSDData(String item, String item2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, item2);

        Log.d(TAG, "addCPUData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        db.close();
    }
}