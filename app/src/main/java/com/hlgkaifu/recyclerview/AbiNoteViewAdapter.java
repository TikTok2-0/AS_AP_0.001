package com.hlgkaifu.recyclerview;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentController;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hlgkaifu.recyclerview.AbirechnerActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AbiNoteViewAdapter extends RecyclerView.Adapter<AbiNoteViewAdapter.Viewholder> {

    private Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private ArrayList<com.hlgkaifu.recyclerview.AbiNote> Abinoten = new ArrayList<>();
    private AbirechnerActivity abirechnerActivity;

    public AbiNoteViewAdapter(Context context, AbirechnerActivity abirechnerActivity, ArrayList<com.hlgkaifu.recyclerview.AbiNote> abinoten) {
        this.context = context;
        this.abirechnerActivity = abirechnerActivity;
        this.Abinoten = abinoten;
        sharedPreferences = abirechnerActivity.getSharedPreferences(
                "mainPreferenceKey", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @Override
    public AbiNoteViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.abinote_list_item,parent,false);
        return new AbiNoteViewAdapter.Viewholder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull AbiNoteViewAdapter.Viewholder holder, int position) {

        holder.edtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abirechnerActivity.openEdtBottomSheet(position,Abinoten.get(position));
            }
        });

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

        if(Abinoten.get(holder.getAdapterPosition()).isExam()){
            holder.pointsAbitur.setText(String.valueOf(Abinoten.get(holder.getAdapterPosition()).getExamGrade()));
        }else{
            holder.pointsAbitur.setText("-");
        }

    }

    @Override
    public int getItemCount() {
        try{
            return Abinoten.size();
        }catch(Exception e){
            System.out.println(e);
        }
        return 0;
         }

    public void removeAbinote(int position){
        Abinoten.remove(position);
        notifyItemRemoved(position);
        abirechnerActivity.updateAbinoten(Abinoten);
        abirechnerActivity.updateStats();
        setList("abinoten", Abinoten);
    }

    public void addAbinote(com.hlgkaifu.recyclerview.AbiNote abiNote){
        Abinoten.add(abiNote);
        notifyDataSetChanged();
        abirechnerActivity.updateAbinoten(Abinoten);
        abirechnerActivity.updateStats();
        setList("abinoten", Abinoten);
    }

    public void edtAbinote(int position, com.hlgkaifu.recyclerview.AbiNote abinote){
        Abinoten.get(position).setCourse(abinote.getCourse());
        Abinoten.get(position).setPointsSem1(abinote.getPointsSem1());
        Abinoten.get(position).setPointsSem2(abinote.getPointsSem2());
        Abinoten.get(position).setPointsSem3(abinote.getPointsSem3());
        Abinoten.get(position).setPointsSem4(abinote.getPointsSem4());
        Abinoten.get(position).setExam(abinote.isExam());
        Abinoten.get(position).setExamGrade(abinote.getExamGrade());
        Abinoten.get(position).setHigherLevel(abinote.isHigherLevel());
        notifyDataSetChanged();
        abirechnerActivity.updateAbinoten(Abinoten);
        abirechnerActivity.updateStats();
        setList("abinoten", Abinoten);
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        private ImageView edtBtn;
        private TextView course;
        private TextView pointsS1;
        private TextView pointsS2;
        private TextView pointsS3;
        private TextView pointsS4;
        private TextView level;
        private TextView pointsAbitur;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            edtBtn = itemView.findViewById(R.id.edtBtn);
            course = itemView.findViewById(R.id.course);
            pointsS1 = itemView.findViewById(R.id.pointsS1);
            pointsS2 = itemView.findViewById(R.id.pointsS2);
            pointsS3 = itemView.findViewById(R.id.pointsS3);
            pointsS4 = itemView.findViewById(R.id.pointsS4);
            level = itemView.findViewById(R.id.level);
            pointsAbitur = itemView.findViewById(R.id.pointsAbitur);
        }

    }

    public <T> void setList(String key, ArrayList<T> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println("----------------"+json);
        set(key,json);
    }

    public static void set(String key, String value){
        System.out.println("-------------"+editor);
        editor.putString(key,value);
        editor.apply();
    }

    public ArrayList<com.hlgkaifu.recyclerview.AbiNote> getList(String key){
        ArrayList<com.hlgkaifu.recyclerview.AbiNote> arrayItems = null;
        String serializedObject = sharedPreferences.getString(key,null);
        if(serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<com.hlgkaifu.recyclerview.AbiNote>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject,type);
        }
        return arrayItems;
    }
}
