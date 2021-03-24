package com.example.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeworkViewAdapter extends RecyclerView.Adapter<HomeworkViewAdapter.ViewHolder> {

    private ArrayList<Homework> homeworkList = new ArrayList<>();

    Context context;

    private Activity mainActivity;
    homeworkActivity mainActivityInstance;

    public HomeworkViewAdapter(Context context, Activity activity, homeworkActivity mainActivityInstance){
        this.context = context;
        this.mainActivity = activity;
        this.mainActivityInstance = mainActivityInstance;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_list_item,parent,false);
        return new HomeworkViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkViewAdapter.ViewHolder holder, int position) {
        System.out.println("-----------Adapter: "+homeworkList.get(position).getSubject()+", "+homeworkList.get(position).getExtraInfo());
        try{
        holder.subject.setText(homeworkList.get(position).getSubject());
        //holder.extraInf.setText(homeworkList.get(position).getExtraInfo());
            TextView extraInfos = holder.extraInf;
            System.out.println("---------Adaptet Extra Info: "+extraInfos.getText());
            //extraInfos = mainActivityInstance.findViewById(R.id.extInfTxt);
            extraInfos.setText(homeworkList.get(position).getExtraInfo());
        holder.date.setText(homeworkList.get(position).getDateStr());
        }catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(mainActivity,"Hausaufgaben können nicht geladen werden",Toast.LENGTH_SHORT).show();
        }

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                homeworkList.remove(holder.getAdapterPosition());
                Homework.homeworkList = homeworkList;
                notifyItemRemoved(holder.getAdapterPosition());


                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView subject, date, extraInf;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            extraInf = itemView.findViewById(R.id.extraInf);
            subject = itemView.findViewById(R.id.subject);
            date = itemView.findViewById(R.id.date);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }
    public void updateHomework(){
        homeworkList = Homework.homeworkList;
    }
}
