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
import java.util.Calendar;

public class RecyclerViewAdapterLessons extends RecyclerView.Adapter<RecyclerViewAdapterLessons.ViewHolder> {

    private OnLessonClickedListener onLessonClickedListener;
    private Context mContext;

    public void setOnLessonClickedListener(OnLessonClickedListener l) {
        onLessonClickedListener = l;
    }


    private static final String TAG = "ViewAdapterLesson";

    //vars
    private ArrayList<Lesson> mLessons = new ArrayList<>();

    public RecyclerViewAdapterLessons(Context context, ArrayList<Lesson> lessons) {
        mLessons = lessons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_lessons_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");


        //holder.name.setText(mNames.get(position));
        Lesson lesson = mLessons.get(position);
        StringBuilder hourEnd = new StringBuilder(String.valueOf(lesson.getInterval().getEnd().getHourOfDay()));
        StringBuilder hourStart = new StringBuilder(String.valueOf(lesson.getInterval().getStart().getHourOfDay()));
        String minuteStart = String.valueOf(lesson.getInterval().getStart().getMinuteOfHour());
        String minuteEnd = String.valueOf(lesson.getInterval().getEnd().getMinuteOfHour());
        if(minuteEnd.length() == 1){minuteEnd+="0";}
        if(minuteStart.length() == 1){minuteStart+="0";}
        if(hourStart.length() == 1){hourStart.insert(0,"0");}
        if(hourEnd.length() == 1){hourEnd.insert(0,"0");}
        String editable = lesson.getSubjectName() + " | " + lesson.getTeacherName()
                        + "\n" + hourStart + ":" + minuteStart +
                " - " + hourEnd  + ":" + minuteEnd;
        holder.name.setText(editable);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Lesson whatLesson = mLessons.get(position);
                Log.d(TAG, "onClick: clicked on an lesson: " + whatLesson.getSubjectName());
                //Toast.makeText(mContext, whatAudience.getNumber()+"", Toast.LENGTH_SHORT).show();

                onLessonClickedListener.onLessonClickedListener(whatLesson);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLessons.size();
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
