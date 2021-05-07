package com.example.recyclerview;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class AbiNoteViewAdapter extends RecyclerView.Adapter<AbiNoteViewAdapter.Viewholder> {

    private Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    @NonNull
    @Override
    public AbiNoteViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.abinote_list_item,parent,false);
        sharedPreferences = context.getSharedPreferences(
                "mainPreferenceKey", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new AbiNoteViewAdapter.Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AbiNoteViewAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        private TextView course;
        private TextView pointsS1;
        private TextView pointsS2;
        private TextView pointsS3;
        private TextView pointsS4;
        private TextView level;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            course = itemView.findViewById(R.id.course);
            pointsS1 = itemView.findViewById(R.id.pointsS1);
            pointsS2 = itemView.findViewById(R.id.pointsS2);
            pointsS3 = itemView.findViewById(R.id.pointsS3);
            pointsS4 = itemView.findViewById(R.id.pointsS4);
            level = pointsS1 = itemView.findViewById(R.id.level);
        }

    }
}
