package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBTeachers extends SQLiteOpenHelper {
    private static DBTeachers sInstance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "teachers_database";

    public static final String TABLE_TEACHERS = "teachers";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_TIME = "time";
    public static final String KEY_NUMBER_OF_AUD = "audience";



    static final String[] COLUMNS = {KEY_ID, TABLE_TEACHERS, KEY_NAME,KEY_SURNAME, KEY_SUBJECT ,KEY_TIME, KEY_NUMBER_OF_AUD};

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
                + " integer primary key," + KEY_NAME + " text," + KEY_SURNAME + " text," + KEY_SUBJECT + " text," +
                KEY_TIME + " integer," + KEY_NUMBER_OF_AUD + " integer" + ")");
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
            int timeIndex = cursor.getColumnIndex(KEY_TIME);
            int audienceIndex = cursor.getColumnIndex(KEY_NUMBER_OF_AUD);
            do{
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", name = " + cursor.getInt(nameIndex)+
                        ", surname = " + cursor.getInt(surnameIndex)+
                        ", subject = " + cursor.getString(subjectIndex)+
                        ", time = " + cursor.getString(timeIndex)+
                        ", audience= " + cursor.getInt(audienceIndex));
            }while (cursor.moveToNext());
        }else
            Log.d("mLog","0 rows");
        cursor.close();
        db.close();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Lesson> getLessonsList(){
        List<Lesson> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHERS, null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME);
            int surnameIndex = cursor.getColumnIndex(KEY_SURNAME);
            int subjectIndex = cursor.getColumnIndex(KEY_SUBJECT);
            int timeIndex = cursor.getColumnIndex(KEY_TIME);
            int numberIndex = cursor.getColumnIndex(KEY_NUMBER_OF_AUD);
            do{
                Calendar now = Calendar.getInstance();
                LocalDate d = LocalDate.now();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.forLanguageTag("uk-UA"));

                List<String> schedules = Arrays.asList(cursor.getString(timeIndex).split(","));
                for(String dayAndHour: schedules){
                    //get day in int and hour strings
                    DayOfWeek day = DayOfWeek.valueOf(dayAndHour.substring(0,dayAndHour.indexOf("(")).toUpperCase().trim());
                    String firstHour = dayAndHour.substring(dayAndHour.indexOf("(")+1,dayAndHour.indexOf("-"));
                    String secondHour = dayAndHour.substring(dayAndHour.indexOf("-")+1);

                    //get day of month by current week and day
                    LocalDate date = d.with(TemporalAdjusters.dayOfWeekInMonth(now.get(Calendar.WEEK_OF_MONTH)-1, day));
                    if(date.getDayOfMonth()<now.get(Calendar.DAY_OF_MONTH)){
                        date = d.with(TemporalAdjusters.dayOfWeekInMonth(now.get(Calendar.WEEK_OF_MONTH), day));
                    }
                    //parse hour and minutes
                    Calendar c1 = Calendar.getInstance();
                    Calendar c2 = Calendar.getInstance();
                    try {
                    Date dateStartHour = sdf.parse(firstHour);
                    c1.setTime(dateStartHour);
                    Date dateEndHour = sdf.parse(secondHour);
                    c2.setTime(dateEndHour);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //get start and end date to create interval object
                    DateTime start = new DateTime(now.get(Calendar.YEAR), date.getMonthValue(), date.getDayOfMonth(), c1.get(Calendar.HOUR_OF_DAY), c1.get(Calendar.MINUTE), 0, 0);
                    DateTime end = new DateTime(now.get(Calendar.YEAR), date.getMonthValue(), date.getDayOfMonth(), c2.get(Calendar.HOUR_OF_DAY), c2.get(Calendar.MINUTE), 0, 0);
                    Interval interval = new Interval(start, end);
                    Log.v("interval", "Start: " + start.toString() +", End: " + end.toString());

                    list.add(new Lesson(cursor.getString(nameIndex),cursor.getString(surnameIndex),
                            cursor.getString(subjectIndex),interval,cursor.getInt(numberIndex)));
                }

            }while (cursor.moveToNext());
        }else
            Log.d("mLog","0 rows");
        cursor.close();
        db.close();
        return list;
    }

}
