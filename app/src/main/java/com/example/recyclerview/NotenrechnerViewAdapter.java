package com.example.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotenrechnerViewAdapter extends RecyclerView.Adapter<NotenrechnerViewAdapter.Viewholder> {

    private ArrayList<Note> Noten = new ArrayList<>();
    private Context context;
    private NotenrechnerActivity mainActivityInstance;
    private NotenrechnerActivity notenrechnerActivity;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String[] fächer = new String[]{
            "Mathe", "Physik", "Deutsch", "Englisch", "Kunst", "Band", "Biologie", "Chemie", "Wirtschaft", "Französisch", "Latein", "Spanisch", "Geographie", "Informatik", "Musik", "Natur und Technik", "NWP", "Orchester", "PGW", "Philosophie", "Psychologie", "Religion", "Sport", "Theater", "Geschichte"
    };

    private static String[] noten = new String[]{
            "1+", "1","1-","2+", "2","2-","3+", "3","3-","4+", "4","4-","5+", "5","5-","6"
    };

    public NotenrechnerViewAdapter(Context context, NotenrechnerActivity mainActivityInstance, ArrayList<Note> Noten) {
        this.context = context;
        this.mainActivityInstance = mainActivityInstance;
        this.Noten = Noten;
    }

    @NonNull
    @Override
    public NotenrechnerViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notenrechner_list_item,parent,false);
        sharedPreferences = context.getSharedPreferences(
                "mainPreferenceKey", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        return new NotenrechnerViewAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotenrechnerViewAdapter.Viewholder holder, int position1) {

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("----------------"+holder.getAdapterPosition());
                Noten.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                mainActivityInstance.changeDurchschnitt(Noten);
                setList("list", Noten);
                return false;
            }
        });

        holder.Fach.setText(Noten.get(holder.getAdapterPosition()).getFach());
        ArrayAdapter<String> adapterFach = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, fächer);
        holder.Fach.setAdapter(adapterFach);

        holder.Fach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String newfach = holder.Fach.getText().toString();
                Noten.get(holder.getAdapterPosition()).setFach(newfach);
                setList("list", Noten);
            }
        });


        ArrayAdapter<CharSequence> adapterNote = ArrayAdapter.createFromResource(context, R.array.noten, android.R.layout.simple_spinner_dropdown_item);
        adapterFach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.Note.setAdapter(adapterNote);

        for(int i=0; i<noten.length;i++){
            if(noten[i].equals(Noten.get(holder.getAdapterPosition()).getNote())){
                holder.Note.setSelection(i);
            }
        }
        holder.Note.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                String newnote = parent.getItemAtPosition(position2).toString();
                Noten.get(holder.getAdapterPosition()).setNote(newnote);
                mainActivityInstance.changeDurchschnitt(Noten);
                //System.out.println("----------------"+Noten.get(position1).getNote());
                setList("list", Noten);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return Noten.size();
    }

    public void addNote(Note note){
        Noten.add(note);
    }

    public ArrayList<Note> getNoten(){
        return Noten;
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private AutoCompleteTextView Fach;
        private Spinner Note;
        private CardView cardView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.parent);
            Fach = itemView.findViewById(R.id.actvfach);
            Note = itemView.findViewById(R.id.spinnernote);

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
