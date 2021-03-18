package com.example.recyclerview;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotenrechnerViewAdapter extends RecyclerView.Adapter<NotenrechnerViewAdapter.Viewholder> {

    private ArrayList<Note> Noten = new ArrayList<>();
    private Context context;
    NotenrechnerActivity notenrechnerActivity;

    private static final String[] fächer = new String[]{
            "Mathe", "Physik", "Deutsch", "Englisch", "Kunst", "Band", "Biologie", "Chemie", "Wirtschaft", "Französisch", "Latein", "Spanisch", "Geographie", "Informatik", "Musik", "Natur und Technik", "NWP", "Orchester", "PGW", "Philosophie", "Psychologie", "Religion", "Sport", "Theater"
    };

    private static String[] noten;

    public NotenrechnerViewAdapter(Context context) {
        this.context = context;

        notenrechnerActivity = new NotenrechnerActivity();
        //Noten.add(new Note("Mathe", "2"));
        //Noten.add(new Note("Deutsch", "2"));
        //Noten.add(new Note("Englisch", "2"));
    }

    @NonNull
    @Override
    public NotenrechnerViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notenrechner_list_item,parent,false);
        return new NotenrechnerViewAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotenrechnerViewAdapter.Viewholder holder, int position1) {

        holder.Fach.setText(Noten.get(position1).getFach());

        ArrayAdapter<String> adapterFach = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, fächer);
        holder.Fach.setAdapter(adapterFach);

        //holder.Note.setText(Noten.get(position).getNote());
        ArrayAdapter<CharSequence> adapterNote = ArrayAdapter.createFromResource(context, R.array.noten, android.R.layout.simple_spinner_dropdown_item);
        adapterFach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.Note.setAdapter(adapterNote);

        holder.Note.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                String newnote = parent.getItemAtPosition(position2).toString();
                Noten.get(position1).setNote(newnote);
                System.out.println("------------------"+Noten.get(position1).getNote());
                notenrechnerActivity.changeDurchschnitt(Noten);
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

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            Fach = itemView.findViewById(R.id.actvfach);
            Note = itemView.findViewById(R.id.spinnernote);

        }


    }
}
