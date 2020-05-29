package com.example.myapplication.audiences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AudiencesFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private RecyclerViewAdapterFloors adapterFloor;
    private RecyclerViewAdapterAudiences adapterAud;
    //vars
    private ArrayList<String> floors = new ArrayList<>();
    private ArrayList<Audience> audiences = new ArrayList<>();
    private DBAudiences dbAudience;

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

  /*    LinearLayoutManager layoutManagerAud = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewAud = rootView.findViewById(R.id.recyclerView);
        recyclerViewAud.setLayoutManager(layoutManagerAud);
        RecyclerViewAdapter adapterAud = new RecyclerViewAdapter(getContext(), floors);
        recyclerViewAud.setAdapter(adapterAud);
    */

        return rootView;
    }

    // Do all the job here (like creating database object, doing some job with changing information, etc...
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbAudience = DBAudiences.getInstance(getContext());
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

                adapterAud.setOnAudienceClickedListener(new OnAudienceClickedListener() {
                    @Override
                    public void onAudienceClickedListener(Audience audience) {
                        Toast.makeText(getContext(), audience.getNumber()+"", Toast.LENGTH_SHORT).show();
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

}
