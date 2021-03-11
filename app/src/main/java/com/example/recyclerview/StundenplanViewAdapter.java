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

        switch(courses.get(position).getCourse()){
            case "Mathe":
                holder.courseIcon.setImageResource(R.drawable.mathicon);
                break;
            case "Physik":
                holder.courseIcon.setImageResource(R.drawable.physicsicon);
                break;
            case "Deutsch":
                holder.courseIcon.setImageResource(R.drawable.germanicon);
                break;
            case "Englisch":
                holder.courseIcon.setImageResource(R.drawable.englishicon);
                break;
            case "Kunst":
                holder.courseIcon.setImageResource(R.drawable.articon);
                break;
            case "Band":
                holder.courseIcon.setImageResource(R.drawable.bandicon);
                break;
            case "Biologie":
                holder.courseIcon.setImageResource(R.drawable.bioicon);
                break;
            case "Chemie":
                holder.courseIcon.setImageResource(R.drawable.chemistryicon);
                break;
            case "Wirtschaft":
                holder.courseIcon.setImageResource(R.drawable.economyicon);
                break;
            case "Französisch":
                holder.courseIcon.setImageResource(R.drawable.frenchlatinspanishicon);
                break;
            case "Latein":
                holder.courseIcon.setImageResource(R.drawable.frenchlatinspanishicon);
                break;
            case "Spanisch":
                holder.courseIcon.setImageResource(R.drawable.frenchlatinspanishicon);
                break;
            case "Geographie":
                holder.courseIcon.setImageResource(R.drawable.geographyicon);
                break;
            case "Geschichte":
                holder.courseIcon.setImageResource(R.drawable.historyicon);
                break;
            case "Informatik":
                holder.courseIcon.setImageResource(R.drawable.iticon);
                break;
            case "Musik":
                holder.courseIcon.setImageResource(R.drawable.musicicon);
                break;
            case "Natur und Technik":
                holder.courseIcon.setImageResource(R.drawable.nticon);
                break;
            case "NWP":
                holder.courseIcon.setImageResource(R.drawable.nwpicon);
                break;
            case "Orchester":
                holder.courseIcon.setImageResource(R.drawable.orchestraicon);
                break;
            case "PGW":
                holder.courseIcon.setImageResource(R.drawable.pgwicon);
                break;
            case "Philosophie":
                holder.courseIcon.setImageResource(R.drawable.philosophyicon);
                break;
            case "Psychologie":
                holder.courseIcon.setImageResource(R.drawable.psychologyicon);
                break;
            case "Religion":
                holder.courseIcon.setImageResource(R.drawable.religionicon);
                break;
            case "Seminar":
                holder.courseIcon.setImageResource(R.drawable.seminaricon);
                break;
            case "Sport":
                holder.courseIcon.setImageResource(R.drawable.sporticon);
                break;
            case "Theater":
                holder.courseIcon.setImageResource(R.drawable.theatreicon);
                break;

        }

        switch (courses.get(position).getStatus()){
            case "Unterricht":
                holder.statusIcon.setImageResource(R.drawable.unterrichticon);
                break;
            case "Vertretung":
                holder.statusIcon.setImageResource(R.drawable.vertretungicon);
                break;
            case "Entfall":
                holder.statusIcon.setImageResource(R.drawable.ausfallicon);
                break;

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
