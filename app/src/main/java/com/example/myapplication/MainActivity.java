package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.MenuItem;



import androidx.appcompat.app.AppCompatActivity;




import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {
    DBTeachers dbAssist;
    DBAudiences dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAssist = DBTeachers.getInstance(this);
        dbHelper = DBAudiences.getInstance(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        SQLiteDatabase database_teachers = dbAssist.getWritableDatabase();
        dbAssist.logDB();
        dbHelper.logDB();
        database_teachers.close();
        database.close();
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
