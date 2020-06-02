package com.example.myapplication.audiences;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class RecyclerViewAdapterAudiences extends RecyclerView.Adapter<RecyclerViewAdapterAudiences.ViewHolder> {

    private OnAudienceClickedListener onAudienceClickedListener;
    private Context mContext;

    void setOnAudienceClickedListener(OnAudienceClickedListener l) {
        onAudienceClickedListener = l;
    }


    private static final String TAG = "ViewAdapterAudiences";

    //vars
    private ArrayList<Audience> mAudiences = new ArrayList<>();

    RecyclerViewAdapterAudiences(Context context, ArrayList<Audience> audiences) {
        mAudiences = audiences;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_audiences_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");


        //holder.name.setText(mNames.get(position));
        String editable = String.valueOf(mAudiences.get(position).getNumber());
        if(mAudiences.get(position).getTf() == 1){
            holder.name.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.colorAccent));
        }
        holder.name.setText(editable);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Audience whatAudience = mAudiences.get(position);

                Log.d(TAG, "onClick: clicked on an image: " + whatAudience.getNumber());
                //Toast.makeText(mContext, whatAudience.getNumber()+"", Toast.LENGTH_SHORT).show();

                onAudienceClickedListener.onAudienceClickedListener(whatAudience);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAudiences.size();
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
