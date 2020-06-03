package com.example.myapplication.teachers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.audiences.Lesson;
import com.example.myapplication.audiences.OnLessonClickedListener;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class RecyclerViewAdapterLessonsTeacher extends RecyclerView.Adapter<RecyclerViewAdapterLessonsTeacher.ViewHolder> {

    private OnLessonClickedListener onLessonClickedListener;
    private Context mContext;
    private Set<String> daysHash = new LinkedHashSet<>();

    private Map<Integer, String> daysE;


    public void setOnLessonClickedListener(OnLessonClickedListener l) {
        onLessonClickedListener = l;
    }


    private static final String TAG = "ViewAdapterLesson";

    //vars
    private ArrayList<Lesson> mLessons = new ArrayList<>();

    public RecyclerViewAdapterLessonsTeacher(Context context, ArrayList<Lesson> lessons) {
        mLessons = lessons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_layout_list_lessons_item, parent, false);


        daysE = new HashMap<>();
        daysE.put(1,"Monday");daysE.put(2,"Tuesday");daysE.put(3,"Wednesday");
        daysE.put(4,"Thursday");daysE.put(5,"Friday");daysE.put(6,"Saturday");daysE.put(7,"Sunday");
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
        String editable = lesson.getSubjectName()
                        + "\n" + hourStart + ":" + minuteStart +
                " - " + hourEnd  + ":" + minuteEnd;
        holder.name.setText(editable);



        String day = daysE.get(Integer.parseInt(lesson.getInterval().getStart().dayOfWeek().getAsString()));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(287, 0, LinearLayout.LayoutParams.WRAP_CONTENT, 0);
        if(daysHash.contains(day)){
            holder.day.setVisibility(View.INVISIBLE);
            holder.day.setLayoutParams(params);
            holder.th_line.setVisibility(View.INVISIBLE);
            holder.th_line.setLayoutParams(params);
            holder.name.setLayoutParams(params);
            //holder.day.setMar
        }
        else holder.day.setText(day);
        daysHash.add(day);
        Log.v("lalala", daysHash.toString());



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
        TextView day;
        View th_line;

        ViewHolder(View itemView) {
            super(itemView);
            //image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
            day = itemView.findViewById(R.id.day);
            th_line = itemView.findViewById(R.id.th_line);
        }
    }
}
