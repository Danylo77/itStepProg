package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBTeachers extends SQLiteOpenHelper {
    private static DBTeachers sInstance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "teachers_database";

    public static final String TABLE_TEACHERS = "teachers";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "Surname";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_DAY = "day";
    public static final String KEY_TIME = "time";
    public static final String KEY_NUMBER_OF_AUD = "audience";



    static final String[] COLUMNS = {KEY_ID, TABLE_TEACHERS, KEY_NAME,KEY_SURNAME, KEY_SUBJECT,KEY_DAY ,KEY_TIME, KEY_NUMBER_OF_AUD};

    private DBTeachers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBTeachers getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBTeachers(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_TEACHERS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_SURNAME + " text," + KEY_SUBJECT + " text," + KEY_DAY + " text," +
                KEY_TIME + " integer," + KEY_NUMBER_OF_AUD + "integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TEACHERS);
        onCreate(db);
    }
    void logDB(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHERS, null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(KEY_ID); // 0
            int nameIndex = cursor.getColumnIndex(KEY_NAME); // 1
            int surnameIndex = cursor.getColumnIndex(KEY_SURNAME); // 2
            int subjectIndex = cursor.getColumnIndex(KEY_SUBJECT); // 3
            int dayIndex = cursor.getColumnIndex(KEY_DAY);
            int timeIndex = cursor.getColumnIndex(KEY_TIME);
            int numberIndex = cursor.getColumnIndex(KEY_NUMBER_OF_AUD);
            do{
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", floor = " + cursor.getInt(nameIndex)+
                        ", number = " + cursor.getInt(surnameIndex)+
                        ", description = " + cursor.getString(subjectIndex)+
                        ", description = " + cursor.getString(dayIndex)+
                        ", description = " + cursor.getString(timeIndex)+
                        ", tf = " + cursor.getInt(numberIndex));
            }while (cursor.moveToNext());
        }else
            Log.d("mLog","0 rows");
        cursor.close();

    }
}
