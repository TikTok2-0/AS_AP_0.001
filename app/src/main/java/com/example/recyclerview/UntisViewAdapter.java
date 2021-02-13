package com.example.recyclerview;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UntisViewAdapter extends RecyclerView.Adapter<UntisViewAdapter.ViewHolder> {
    private ArrayList<Course> courses = new ArrayList<>();

    private News newsObj;

    private Context context;

    private Activity mainActivity;
    HomeScreen mainActivityInstance;

    private View mContentView;

    //private ActivitySwitcher activitySwitcher;

    public UntisViewAdapter(Context context, Activity activity, HomeScreen mainActivityInstance) {

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
        holder.colorIcon.setColorFilter(context.getColor(courses.get(position).getColorCourse()));

        holder.relativeLayout.setBackgroundColor(context.getColor(courses.get(position).getColorBack()));
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
