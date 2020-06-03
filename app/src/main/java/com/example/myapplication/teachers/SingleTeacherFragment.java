package com.example.myapplication.teachers;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.audiences.Lesson;
import com.example.myapplication.audiences.OnLessonClickedListener;
import com.example.myapplication.audiences.RecyclerViewAdapterLessons;

import java.util.ArrayList;
import java.util.List;

public class SingleTeacherFragment extends Fragment {
    TextView nameTeacher;
    TextView whatSubjects;
    Button backButton;
    private DBTeachers dbTeachers;
    private RecyclerViewAdapterLessons adapterLessons;

    SingleTeacherFragment(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frame_single_teacher, container, false);
        nameTeacher = rootView.findViewById(R.id.teacher_name);
        whatSubjects = rootView.findViewById(R.id.what_studying);
        backButton = rootView.findViewById(R.id.back_button);

        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbTeachers = DBTeachers.getInstance(getContext());

        Bundle bundle = getArguments();
        String teacherName = bundle.getString("TEACHER_NAME", "");
        nameTeacher.setText(teacherName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction mTransaction = getChildFragmentManager().beginTransaction();
                Fragment teacherFragment = new TeachersFragment();
                mTransaction.replace(R.id.singleTeacherFrame, teacherFragment, teacherFragment.getClass().getName());
                mTransaction.commit();
            }
        });

        ArrayList<Lesson> lessons = Lesson.getLessonsByTeacherName(dbTeachers.getLessonsList(), teacherName);

        StringBuilder displaySubj = new StringBuilder("Teaches ");
        List<String> subjList = Lesson.getUniqueSubjects(lessons);
        if(subjList.size() >= 2) {
            for (String sub : subjList) {
                displaySubj.append(sub).append(", ");
            }
            whatSubjects.setText(displaySubj.substring(0, subjList.size() - 2).replace(", ", " and "));
        }
        else whatSubjects.setText(displaySubj.append(subjList.get(0)));
        Log.v("singleTeacher",lessons.toString());


        LinearLayoutManager layoutManagerLessons = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerViewLessons = view.findViewById(R.id.recyclerViewLessonsTeacher);
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
}
