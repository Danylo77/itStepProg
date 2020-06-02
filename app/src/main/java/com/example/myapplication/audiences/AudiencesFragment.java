package com.example.myapplication.audiences;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.teachers.DBTeachers;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AudiencesFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private RecyclerViewAdapterFloors adapterFloor;
    private RecyclerViewAdapterAudiences adapterAud;
    private RecyclerViewAdapterLessons adapterLessons;
    //vars
    private ArrayList<String> floors = new ArrayList<>();
    private ArrayList<Audience> audiences = new ArrayList<>();
    private ArrayList<Lesson> lessons = new ArrayList<>();

    private DBAudiences dbAudience;
    private DBTeachers dbTeachers;

    private TextView days_btn;
    private Calendar timeNow;

    private Map<String,Integer> daysE;

    public AudiencesFragment() {
        //required empty public constructor
    }

    //initialize all views from xml in onCreateView
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frame_audiences, container, false);

        daysE = new HashMap<>();
        daysE.put("Mon",1);daysE.put("Tue",2);daysE.put("Wed",3);
        daysE.put("Thu",4);daysE.put("Fri",5);daysE.put("Sat",6);daysE.put("Sun",7);

        timeNow = Calendar.getInstance();

        initRecycleFloorView();

        LinearLayoutManager layoutManagerFl = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewFloor = rootView.findViewById(R.id.recyclerViewFloor);
        recyclerViewFloor.setLayoutManager(layoutManagerFl);
        adapterFloor = new RecyclerViewAdapterFloors(getContext(), floors);
        recyclerViewFloor.setAdapter(adapterFloor);

        days_btn = rootView.findViewById(R.id.day_of_week_selector);
        Format dateFormat = new SimpleDateFormat("E",Locale.US);
        String res = dateFormat.format(new Date());
        days_btn.setText(res);


        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_days, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        days_btn.setText(item.getTitle());
                        //lessons.clear();
                        //initLessonsInAudienceByDay(audience.getNumber(),daysE.get(item.getTitle()));
                        Toast.makeText(getContext(), "You selected the action : " + item.getTitle()+ " " + daysE.get(item.getTitle()), Toast.LENGTH_SHORT).show();
                        //adapterLessons.notifyDataSetChanged();

                        return true;
                    }

                });
                popup.show();

            }
        };

        days_btn.setOnClickListener(listener);

        return rootView;
    }


    // Do all the job here (like creating database object, doing some job with changing information, etc...
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbAudience = DBAudiences.getInstance(getContext());
        dbTeachers = DBTeachers.getInstance(getContext());
        // when clicked on floor
        adapterFloor.setOnFloorClickedListener(new OnFloorClickedListener() {
            @Override
            public void onFloorClickedListener(int floor) {
                Toast.makeText(getContext(), floor+"", Toast.LENGTH_SHORT).show();
                initFloorAudiences(floor);

                LinearLayoutManager layoutManagerAud = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                RecyclerView recyclerViewAud = view.findViewById(R.id.recyclerViewAudience);
                recyclerViewAud.setLayoutManager(layoutManagerAud);
                adapterAud = new RecyclerViewAdapterAudiences(getContext(), audiences);
                recyclerViewAud.setAdapter(adapterAud);
                lessons.clear();

                // when clicked on audience
                adapterAud.setOnAudienceClickedListener(new OnAudienceClickedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onAudienceClickedListener(Audience audience) {
                        //initLessonsInAudience(audience.getNumber());

                        //initLessonsInAudienceByDay(audience.getNumber(),timeNow.get(Calendar.DAY_OF_WEEK));
                        Log.v("lessons", daysE.get(days_btn.getText().toString().trim())+"");
                        initLessonsInAudienceByDay(audience.getNumber(), daysE.get(days_btn.getText().toString().trim()));
                        Log.v("lessons", lessons.toString());
                        //days_btn.setText("Today");

                        Toast.makeText(getContext(), audience.getNumber()+"", Toast.LENGTH_SHORT).show();
                        LinearLayoutManager layoutManagerLessons = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        RecyclerView recyclerViewLessons = view.findViewById(R.id.recyclerViewLessons);
                        recyclerViewLessons.setLayoutManager(layoutManagerLessons);
                        adapterLessons = new RecyclerViewAdapterLessons(getContext(), lessons);
                        recyclerViewLessons.setAdapter(adapterLessons);


                        // when clicked on lesson
                        adapterLessons.setOnLessonClickedListener(new OnLessonClickedListener() {
                            @Override
                            public void onLessonClickedListener(Lesson lesson) {
                                Toast.makeText(getContext(), lesson.getSubjectName()+"", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });

    }

    private void initRecycleFloorView(){
        floors.add("1");
        floors.add("2");
        floors.add("3");
        floors.add("4");
    }
    private void initFloorAudiences(int floor){
        audiences = dbAudience.getAllAudiencesByFloor(floor);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initLessonsInAudience(int audience) {
        lessons = Lesson.getAllLessonsByAudience(dbTeachers.getLessonsList(), audience);
        Log.v("interval", lessons.get(0).getInterval().getStart().getDayOfWeek() + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initLessonsInAudienceByDay(int audience, int day) {
        lessons = Lesson.getLessonsByAudienceAndDay(dbTeachers.getLessonsList(), audience, day);
        Log.v("lessonsM", lessons.toString());
    }



}
