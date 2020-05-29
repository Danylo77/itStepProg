package com.example.myapplication.audiences;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.teachers.DBTeachers;

import java.util.ArrayList;

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

    public AudiencesFragment() {
        //required empty public constructor
    }

    //initialize all views from xml in onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frame_audiences, container, false);

        initRecycleFloorView();

        LinearLayoutManager layoutManagerFl = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewFloor = rootView.findViewById(R.id.recyclerViewFloor);
        recyclerViewFloor.setLayoutManager(layoutManagerFl);
        adapterFloor = new RecyclerViewAdapterFloors(getContext(), floors);
        recyclerViewFloor.setAdapter(adapterFloor);

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
                        initLessonsInAudience(audience.getNumber());
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
    }



}
