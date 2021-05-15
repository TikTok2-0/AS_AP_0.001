package com.hlgkaifu.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hlgkaifu.recyclerview.Course;
import com.hlgkaifu.recyclerview.HomeScreenActivity;
import com.hlgkaifu.recyclerview.News;

import java.util.ArrayList;

public class UntisViewAdapter extends RecyclerView.Adapter<UntisViewAdapter.ViewHolder> {
    private ArrayList<Course> courses = new ArrayList<>();

    private News newsObj;

    private Context context;

    private Activity mainActivity;
    HomeScreenActivity mainActivityInstance;

    private View mContentView;

    //private ActivitySwitcher activitySwitcher;

    public UntisViewAdapter(Context context, Activity activity, HomeScreenActivity mainActivityInstance) {

        this.context = context;
        this.mainActivity = activity;
        this.mainActivityInstance = mainActivityInstance;

    }

    @NonNull
    @Override
    public UntisViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //                                                                                        wodran soll view attached sein, false weil true reduntant null wenn man sich nicht sicher ist wodran view attached ist
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.untis_list_item, parent, false);

        //ViewHolder holder = new ViewHolder(view);

        return new UntisViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtRoom.setText(courses.get(position).getRoom());
        holder.txtCourse.setText(courses.get(position).getCourse());
        holder.txtTeach.setText(courses.get(position).getTeacher());
        holder.relativeLayout.setBackgroundColor(context.getColor(getCourseColor(courses.get(position).getStatus())) );
        holder.colorIcon.setImageResource(getCourseIcon(courses.get(position).getCourse()));

        //holder.parent.setOnClickListener((View v) -> mainActivityInstance.switchActivity(universalMenu.class, position));
    }



    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged(); //wichtig damit die Daten sich aktualisieren
    }

    private int getCourseColor(String status){
        switch(status){
            case "Entfall":
                return(R.color.red);
            case "Unterricht":
                return(R.color.white);
            case "Vertretung":
                return(R.color.purple);

        }
        return(R.color.white);
    }

    public int getCourseIcon(String course){
        switch(course) {
            case "Mathe":
                return (R.drawable.mathicon);
            case "Physik":
                return (R.drawable.physicsicon);
            case "Deutsch":
                return (R.drawable.germanicon);
            case "Englisch":
                return (R.drawable.englishicon);
            case "English":
                return (R.drawable.englishicon);
            case "Kunst":
                return (R.drawable.articon);
            case "Band":
                return (R.drawable.bandicon);
            case "Biologie":
                return (R.drawable.bioicon);
            case "Chemie":
                return (R.drawable.chemistryicon);
            case "Wirtschaft":
                return (R.drawable.economyicon);
            case "Französisch":
                return (R.drawable.frenchlatinspanishicon);
            case "Latein":
                return (R.drawable.frenchlatinspanishicon);
            case "Spanisch":
                return (R.drawable.frenchlatinspanishicon);
            case "Geographie":
                return (R.drawable.geographyicon);
            case "Geschichte":
                return (R.drawable.historyicon);
            case "Informatik":
                return (R.drawable.iticon);
            case "Musik":
                return (R.drawable.musicicon);
            case "Natur und Technik":
                return (R.drawable.nticon);
            case "NWP":
                return (R.drawable.nwpicon);
            case "Orchester":
                return (R.drawable.orchestraicon);
            case "PGW":
                return (R.drawable.pgwicon);
            case "Philosophie":
                return (R.drawable.philosophyicon);
            case "Psychologie":
                return (R.drawable.psychologyicon);
            case "Religion":
                return (R.drawable.religionicon);
            case "Seminar":
                return (R.drawable.seminaricon);
            case "Sport":
                return (R.drawable.sporticon);
            case "Theater":
                return (R.drawable.theatreicon);
        }
        return(R.drawable.religionicon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTeach,txtRoom,txtCourse; //txtEmail;
        private CardView parent;
        private ImageView colorIcon;


        //private News newsObj;
        private RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTeach = itemView.findViewById(R.id.txtTeach);
            txtCourse = itemView.findViewById(R.id.txtCourse);
            txtRoom = itemView.findViewById(R.id.txtRoom);
            parent = itemView.findViewById(R.id.parent);
            relativeLayout = itemView.findViewById(R.id.parentRelative);
            colorIcon = itemView.findViewById(R.id.colorIcon);



        }
    }
}
