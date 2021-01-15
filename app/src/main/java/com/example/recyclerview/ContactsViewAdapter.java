package com.example.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlendMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ContactsViewAdapter extends RecyclerView.Adapter<ContactsViewAdapter.ViewHolder>{

    private ArrayList<Contact> contacts = new ArrayList<>();

    private Context context;

    private Activity mainActivity;

    private ActivitySwitcher activitySwitcher;

    public ContactsViewAdapter(Context context, Activity activity) {

        this.context = context;
        this.mainActivity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //                                                                                        wodran soll view attached sein, false weil true reduntant null wenn man sich nicht sicher ist wodran view attached ist
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(contacts.get(position).getName());       //hier kann mehr hinzugefügt werden
        //holder.txtEmail.setText(contacts.get(position).getEmail());

        if(holder.txtName.getText().toString().contains("Moodle")){
            holder.txtName.setTextColor(context.getResources().getColor(R.color.black));
            holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.image.setAlpha(255);
        }


        holder.parent.setOnClickListener(new View.OnClickListener(){;
            @Override
            public void onClick(View v){
                Toast.makeText(context, contacts.get(position).getName()+ " Selected",Toast.LENGTH_SHORT).show();
                if(holder.txtName.getText().toString().contains("HLG während Corona")){
                    ActivitySwitcher;   //NavHostFragment.findNavController(ActivitySwitcher.this).navigate(R.id.MainActivity_to_menu1);
                }
            }
        });

        //loading images
        Glide.with(context)
                .asBitmap()
                .load(contacts.get(position).getImageUrl())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged(); //wichtig damit die Daten sich aktualisieren
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName; //txtEmail;
        private CardView parent;
        private ImageView image;
        private ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            parent = itemView.findViewById(R.id.parent);
            constraintLayout = itemView.findViewById(R.id.parentContraint);
            //txtEmail = itemView.findViewById(R.id.txtEmail);

            image = itemView.findViewById(R.id.image);
        }
    }
}
