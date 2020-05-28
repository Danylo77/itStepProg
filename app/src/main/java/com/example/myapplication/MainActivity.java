package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import org.joda.time.Interval;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {
    DBTeachers dbTeacher;
    DBAudiences dbAudience;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbTeacher = DBTeachers.getInstance(this);
        dbAudience = DBAudiences.getInstance(this);
        /*SQLiteDatabase database_aud = dbAudience.getWritableDatabase();
        SQLiteDatabase database_teachers = dbTeacher.getWritableDatabase();*/
        dbAudience.logDB();
        dbTeacher.logDB();

        List<Lesson> lessons = dbTeacher.getLessonsList();

        // get current time
        java.util.TimeZone tz = java.util.TimeZone.getTimeZone("GMT+3");
        java.util.Calendar c = java.util.Calendar.getInstance(tz);
        Log.v("current", c.getTime().toString());

        List<Integer> busyAudiencesList = new ArrayList<>();
        for(Lesson lesson: lessons){
            if(lesson.getInterval().contains(c.getTimeInMillis())){
                busyAudiencesList.add(lesson.getAudNumber());
                Log.v("ifActive", "Lessons running now: " + lesson.toString());
            }
        }
        dbAudience.writeBusyAudiences(busyAudiencesList);



        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                return false;
            }
        });
    }
}
