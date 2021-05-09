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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AbiNoteViewAdapter extends RecyclerView.Adapter<AbiNoteViewAdapter.Viewholder> {

    private Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private ArrayList<AbiNote> Abinoten = new ArrayList<>();
    private AbirechnerActivity abirechnerActivity;

    public AbiNoteViewAdapter(Context context, AbirechnerActivity abirechnerActivity, ArrayList<AbiNote> abinoten) {
        this.context = context;
        this.abirechnerActivity = abirechnerActivity;
        this.Abinoten = abinoten;
    }

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
        holder.course.setText(Abinoten.get(holder.getAdapterPosition()).getCourse());
        holder.pointsS1.setText(String.valueOf(Abinoten.get(holder.getAdapterPosition()).getPointsSem1()));
        holder.pointsS2.setText(String.valueOf(Abinoten.get(holder.getAdapterPosition()).getPointsSem2()));
        holder.pointsS3.setText(String.valueOf(Abinoten.get(holder.getAdapterPosition()).getPointsSem3()));
        holder.pointsS4.setText(String.valueOf(Abinoten.get(holder.getAdapterPosition()).getPointsSem4()));
        if(Abinoten.get(holder.getAdapterPosition()).isHigherLevel()){
            holder.level.setText("E");
        }else{
            holder.level.setText("G");
        }
    }

    @Override
    public int getItemCount() { return Abinoten.size(); }

    public void removeAbinote(int position){
        Abinoten.remove(position);
        notifyItemRemoved(position);
        //mainActivityInstance.changeDurchschnitt(Noten);
        setList("listAbinoten", Abinoten);
    }

    public void addAbinote(AbiNote abiNote){
        Abinoten.add(abiNote);
        notifyDataSetChanged();
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

    public <T> void setList(String key, ArrayList<T> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println("----------------"+json);
        set(key,json);
    }

    public static void set(String key, String value){
        editor.putString(key,value);
        editor.apply();
    }

    public ArrayList<Note> getList(String key){
        ArrayList<Note> arrayItems = null;
        String serializedObject = sharedPreferences.getString(key,null);
        if(serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Note>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject,type);
        }
        return arrayItems;
    }
}
