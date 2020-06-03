package com.example.myapplication.teachers;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.audiences.DBAudiences;
import com.example.myapplication.audiences.Lesson;

import java.util.ArrayList;
import java.util.Locale;

public class TeachersFragment extends Fragment implements SearchView.OnQueryTextListener {
    SearchView c_searchBar;
    ListView list_teachers;
    private ArrayList<String> teachers;
    private ArrayAdapter<String> arrayAdapter;
    private DBTeachers dbTeachers;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frame_teachers, container, false);
        list_teachers = rootView.findViewById(R.id.list_teachers);
        c_searchBar =  rootView.findViewById(R.id.c_searchBar);

        dbTeachers = DBTeachers.getInstance(getContext());
        teachers = Lesson.getTeachersList(dbTeachers.getLessonsList());
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, teachers);
        list_teachers.setAdapter(arrayAdapter);
        c_searchBar.setOnQueryTextListener(this);
        list_teachers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String teacherName = list_teachers.getItemAtPosition(position).toString();
                Bundle b = new Bundle();
                b.putString("TEACHER_NAME", teacherName);
                FragmentTransaction mTransaction = getChildFragmentManager().beginTransaction();

                Fragment singleTeacherFragment = new SingleTeacherFragment();
                singleTeacherFragment.setArguments(b);
                mTransaction.replace(R.id.teachersFrame, singleTeacherFragment, singleTeacherFragment.getClass().getName());
                mTransaction.commit();
            }
        });

        return rootView;
    }

    // Do all the job here (like creating database object, doing some job with changing information, etc...
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //textTitle.setText("Doing some things with fragments");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teachers.clear();
        if (charText.length() == 0) {
            teachers.addAll(Lesson.getTeachersList(dbTeachers.getLessonsList()));
        } else {
            for (String name : Lesson.getTeachersList(dbTeachers.getLessonsList())) {
                if (name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    teachers.add(name);
                }
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        filter(text);
        return false;
    }
}
