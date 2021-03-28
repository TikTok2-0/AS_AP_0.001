package com.example.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Function;

public class HomeworkViewAdapter extends RecyclerView.Adapter<HomeworkViewAdapter.ViewHolder>
                                    {

    private ArrayList<Homework> homeworkList = new ArrayList<>();

    Context context;

    private Activity mainActivity;
    homeworkActivity mainActivityInstance;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public HomeworkViewAdapter(Context context, Activity activity, homeworkActivity mainActivityInstance){
        this.context = context;
        this.mainActivity = activity;
        this.mainActivityInstance = mainActivityInstance;
        sharedPreferences = mainActivityInstance.getSharedPreferences(mainActivityInstance.getString(R.string.mainPreferenceKey),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_list_item,parent,false);

        return new HomeworkViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkViewAdapter.ViewHolder holder, int position) {
        //System.out.println("-----------Adapter: "+homeworkList.get(position).getSubject()+", "+homeworkList.get(position).getExtraInfo());
        try{
        holder.subject.setText(homeworkList.get(holder.getAdapterPosition()).getSubject());
        //holder.extraInf.setText(homeworkList.get(position).getExtraInfo());
            TextView extraInfos = holder.extraInf;
            //System.out.println("---------Adaptet Extra Info: "+extraInfos.getText());
            //extraInfos = mainActivityInstance.findViewById(R.id.extInfTxt);
            extraInfos.setText(homeworkList.get(holder.getAdapterPosition()).getExtraInfo());
            holder.date.setText(homeworkList.get(holder.getAdapterPosition()).getDateStr());
        }catch (Exception e){
            e.printStackTrace();

        }

        if(mainActivityInstance.switchHomework.isChecked()){

            try{
                holder.checkBox.setChecked(Homework.homeworkList.get(holder.getAdapterPosition()).isCompleted());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            //holder.checkBox.setChecked(false);
        }

        Date currentDate = new Date();
        currentDate.setTime(System.currentTimeMillis());
        try{

            if(homeworkList.get(holder.getAdapterPosition()).getDate()!=null && homeworkList.get(holder.getAdapterPosition()).getDate().compareTo(currentDate)<=0){

                holder.date.setTextColor(mainActivityInstance.getColor(R.color.red));
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(mainActivityInstance,"Something went wrong",Toast.LENGTH_SHORT).show();
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //if(!holder.checkBox.isPressed()){
                    if(isChecked){
                        Homework.homeworkList.get(holder.getAdapterPosition()).setCompleted(true);

                    }else{

                        Homework.homeworkList.get(holder.getAdapterPosition()).setCompleted(false);

                    }
                    Homework.updateActiveHomework();
                    sort(Homework.homeworkList,mainActivityInstance.getString(R.string.homeworkPreferenceKey));
                    updateHomework();
                    for(int i = 0; i<Homework.homeworkList.size();i++){
                        System.out.println(i+"homework: "+Homework.homeworkList.get(i).toString());
                    }
                //}
            }
        });



        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                homeworkList.remove(holder.getAdapterPosition());
                Homework.homeworkList = homeworkList;
                setList(mainActivityInstance.getString(R.string.homeworkPreferenceKey),homeworkList);
                notifyItemRemoved(holder.getAdapterPosition());
                //System.out.println("---------"+holder.getPosition()+" removed");


                return false;
            }
        });
        holder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"subject clicked",Toast.LENGTH_SHORT).show();
                HomeworkBottomSheetDialog bottomSheet = new HomeworkBottomSheetDialog(
                        mainActivityInstance.getSupportFragmentManager(),
                        mainActivityInstance.getAdapter(),
                        mainActivityInstance,true,
                        holder.date.getText().toString(),
                        holder.subject.getText().toString(),
                        holder.extraInf.getText().toString(),
                        holder.getBindingAdapterPosition());

                bottomSheet.show(mainActivityInstance.getSupportFragmentManager(), "homeworkBottomSheet");
                //bottomSheet.setData(holder.date.getText().toString(),holder.subject.getText().toString(),holder.extraInf.getText().toString());
            }
        });


        try{
        holder.date.setText(homeworkList.get(holder.getAdapterPosition()).getDateStr());
        }catch (NullPointerException e){
            holder.date.setText("");
        }


        try{
            holder.checkBox.setChecked(Homework.homeworkList.get(holder.getAdapterPosition()).isCompleted());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }

    public void hideItem(final int position){

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView subject, date, extraInf;
        private CardView cardView;
        private CheckBox checkBox;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            extraInf = itemView.findViewById(R.id.extraInf);
            subject = itemView.findViewById(R.id.subject);
            date = itemView.findViewById(R.id.date);
            cardView = itemView.findViewById(R.id.cardView);
            checkBox = itemView.findViewById(R.id.checkbox);


        }
    }
    public void updateHomework(){



    Homework.updateActiveHomework();
    if(mainActivityInstance.switchHomework.isChecked()){
        homeworkList = Homework.homeworkList;
    }else{
        homeworkList = Homework.activeHomwork;
    }


    }

    public void sort(ArrayList<Homework> list, String key){
        Comparator<Homework> comparator = new  Comparator<Homework>() {
            @Override
            public int compare(Homework o1, Homework o2) {

                if((o1.isCompleted() && o2.isCompleted()) || (!o1.isCompleted() && !o2.isCompleted())) {
                    try {

                        return o1.getDate().compareTo(o2.getDate());


                    } catch (Exception e) {


                        if (o1.getDate() == null && o2.getDate() == null) return 0;
                        else if (o1.getDate() == null) return 1;
                        else return -1;
                    }
                }else{
                    if(o1.isCompleted() && !o2.isCompleted()){
                        return 1;
                    }
                    if(o2.isCompleted() && !o1.isCompleted()){
                        return -1;
                    }
                }return 0;


            }


        };


        Collections.sort(list,comparator);
        setList(key,list);
        //mainActivityInstance.getString(R.string.homeworkPreferenceKey)

    }
    public <T> void setList(String key, ArrayList<T> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println("----------------"+json);
        set(key,json);
    }
    public void set(String key, String value){
        editor.putString(key,value);
        editor.apply();
    }
}
