package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NotenrechnerViewAdapter extends RecyclerView.Adapter<NotenrechnerViewAdapter.Viewholder> {

    private ArrayList<Note> Noten = new ArrayList<>();

    public NotenrechnerViewAdapter() {
        Noten.add(new Note("Mathe", "2"));
        Noten.add(new Note("Deutsch", "2"));
        Noten.add(new Note("Englisch", "2"));
    }

    @NonNull
    @Override
    public NotenrechnerViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notenrechner_list_item,parent,false);
        return new NotenrechnerViewAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotenrechnerViewAdapter.Viewholder holder, int position) {

        holder.Fach.setText(Noten.get(position).getFach());
        holder.Note.setText(Noten.get(position).getNote());
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

        private TextView Fach;
        private TextView Note;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            Fach = itemView.findViewById(R.id.fach);
            Note = itemView.findViewById(R.id.note);




        }


    }
}
