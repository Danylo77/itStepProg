package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAudiences extends SQLiteOpenHelper {
    private static DBAudiences sInstance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database";

    public static final String TABLE_AUDIENCES = "audiences";
    public static final String KEY_ID = "_id";
    public static final String KEY_FLOOR = "floor";
    public static final String KEY_NUMBER_OF_AUD = "number";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TF = "tf";



    static final String[] COLUMNS = {KEY_ID, TABLE_AUDIENCES, KEY_FLOOR, KEY_NUMBER_OF_AUD,KEY_DESCRIPTION,KEY_TF};

    private DBAudiences(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBAudiences getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBAudiences(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_AUDIENCES + "(" + KEY_ID
                + " integer primary key," + KEY_FLOOR + " imteger," + KEY_NUMBER_OF_AUD + " integer," + KEY_DESCRIPTION + " text," +
                KEY_TF + " integer"+ ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_AUDIENCES);
        onCreate(db);
    }
    void logDB(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_AUDIENCES, null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(KEY_ID); // 0
            int floorIndex = cursor.getColumnIndex(KEY_FLOOR); // 1
            int numberIndex = cursor.getColumnIndex(KEY_NUMBER_OF_AUD); // 2
            int descriptionIndex = cursor.getColumnIndex(KEY_DESCRIPTION); // 3
            int tfIndex = cursor.getColumnIndex(KEY_TF);
            do{
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", floor = " + cursor.getInt(floorIndex)+
                        ", number = " + cursor.getInt(numberIndex)+
                        ", description = " + cursor.getString(descriptionIndex)+
                        ", tf = " + cursor.getInt(tfIndex));
            }while (cursor.moveToNext());
        }else
            Log.d("mLog","0 rows");
        cursor.close();

    }
}

