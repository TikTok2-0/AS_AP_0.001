package com.example.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.ArrayList;

public class StundenplanViewAdapter extends RecyclerView.Adapter<StundenplanViewAdapter.ViewHolder> {

    private ArrayList<CourseVP> courses;
    private Context context;
    private ImageView courseIc;
    private ImageView statusIc;

    public StundenplanViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public StundenplanViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stundenplan_recview_item, parent, false);
        //ViewHolder holder = new ViewHolder(view);
        return new StundenplanViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StundenplanViewAdapter.ViewHolder holder, int position) {

        holder.txtRoom.setText(courses.get(position).getRoom());
        holder.txtCourse.setText(courses.get(position).getCourse());
        holder.txtTeacher.setText(courses.get(position).getTeacher());

        if(courses.get(position).getCourse().contains("Mathe")){
            holder.courseIcon.setImageResource(R.drawable.mathicon);
        }else if(courses.get(position).getCourse().contains("Physik")){
            holder.courseIcon.setImageResource(R.drawable.physicsicon);
        }else if(courses.get(position).getCourse().contains("Deutsch")){
            holder.courseIcon.setImageResource(R.drawable.germanicon);
        }else if(courses.get(position).getCourse().contains("Englisch")){
            holder.courseIcon.setImageResource(R.drawable.englishicon);
        }

        if(courses.get(position).getStatus().contains("Unterricht")){
            holder.statusIcon.setImageResource(R.drawable.unterrichticon);
        }else if(courses.get(position).getStatus().contains("Vertretung")){
            holder.statusIcon.setImageResource(R.drawable.vertretungicon);
        }else if(courses.get(position).getStatus().contains("Entfall")){
            holder.statusIcon.setImageResource(R.drawable.ausfallicon);
        }

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(ArrayList<CourseVP> courses) {
        this.courses = courses;
        notifyDataSetChanged(); //wichtig damit die Daten sich aktualisieren
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView courseIcon;
        private TextView txtCourse;
        private TextView txtTeacher;
        private TextView txtRoom;
        private ImageView statusIcon;
        private RelativeLayout relativeLayout;

        public ViewHolder(View itemView){
            super(itemView);
            txtTeacher = itemView.findViewById(R.id.txtTeacher);
            txtCourse = itemView.findViewById(R.id.txtCourse);
            txtRoom = itemView.findViewById(R.id.txtRoom);
            courseIcon = itemView.findViewById(R.id.courseIcon);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            relativeLayout = itemView.findViewById(R.id.parentRelative);

        }




    }

}
