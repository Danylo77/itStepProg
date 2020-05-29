package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.myapplication.audiences.AudiencesFragment;
import com.example.myapplication.audiences.DBAudiences;
import com.example.myapplication.teachers.DBTeachers;
import com.example.myapplication.teachers.TeachersFragment;

import java.util.ArrayList;
import java.util.List;

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
                switch (menuItem.getTitle().toString()){
                    case "Audiences":
                        showFragment(new AudiencesFragment());
                        break;
                    case "Teachers":
                        showFragment(new TeachersFragment());
                }
                Log.v("menuItem",menuItem.getTitle()+"");
                return false;
            }
        });
    }
    public void showFragment(Fragment fragment) {
        FragmentTransaction mTransactiont = getSupportFragmentManager().beginTransaction();

        mTransactiont.replace(R.id.mainFrame, fragment, fragment.getClass().getName());
        mTransactiont.commit();
    }
}
