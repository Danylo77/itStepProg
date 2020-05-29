package com.example.myapplication.audiences;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import java.util.ArrayList;

public class RecyclerViewAdapterFloors extends RecyclerView.Adapter<RecyclerViewAdapterFloors.ViewHolder> {

    private OnFloorClickedListener onFloorClickedListener;

    void setOnFloorClickedListener(OnFloorClickedListener l) {
        onFloorClickedListener = l;
    }


    private static final String TAG = "ViewAdapterFloors";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();

    RecyclerViewAdapterFloors(Context context, ArrayList<String> names) {
        mNames = names;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_floors_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");


        holder.name.setText(mNames.get(position));

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
                //Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();

                int whatFloor = Integer.parseInt(mNames.get(position));
                onFloorClickedListener.onFloorClickedListener(whatFloor);
                //AudiencesFragment.initRecycleAudiences(Integer.parseInt(mNames.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        //CircleImageView image;
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            //image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
        }
    }
}
