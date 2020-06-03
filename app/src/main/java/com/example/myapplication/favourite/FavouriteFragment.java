package com.example.myapplication.favourite;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.audiences.Audience;
import com.example.myapplication.audiences.DBAudiences;
import com.example.myapplication.audiences.Lesson;
import com.example.myapplication.audiences.OnAudienceClickedListener;
import com.example.myapplication.audiences.OnLessonClickedListener;
import com.example.myapplication.audiences.RecyclerViewAdapterAudiences;
import com.example.myapplication.audiences.RecyclerViewAdapterLessons;
import com.example.myapplication.teachers.DBTeachers;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    private RecyclerViewAdapterAudiences adapterAud;
    private RecyclerViewAdapterLessonsFavourite adapterLessons;

    private DBAudiences dbAudience;
    private DBTeachers dbTeachers;
    private ArrayList<Audience> audiences = new ArrayList<>();
    private ArrayList<Lesson> lessons = new ArrayList<>();


    public FavouriteFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.frame_favourite, container, false);
        dbAudience = DBAudiences.getInstance(getContext());
        dbTeachers = DBTeachers.getInstance(getContext());

        audiences = dbAudience.getFavouriteAudiences();

        LinearLayoutManager layoutManagerAud = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewAud = rootView.findViewById(R.id.recyclerViewAudience);
        recyclerViewAud.setLayoutManager(layoutManagerAud);
        adapterAud = new RecyclerViewAdapterAudiences(getContext(), audiences);
        recyclerViewAud.setAdapter(adapterAud);
        adapterAud.setOnAudienceClickedListener(new OnAudienceClickedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAudienceClickedListener(Audience audience) {
                lessons = Lesson.getLessonsByAudience(dbTeachers.getLessonsList(), audience.getNumber());

                LinearLayoutManager layoutManagerLessons = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                final RecyclerView recyclerViewLessons = rootView.findViewById(R.id.recyclerViewLessons);
                recyclerViewLessons.setLayoutManager(layoutManagerLessons);
                adapterLessons = new RecyclerViewAdapterLessonsFavourite(getContext(), lessons);
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


        return rootView;
    }
}
