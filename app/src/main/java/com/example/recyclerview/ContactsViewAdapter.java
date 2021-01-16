package com.example.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ContactsViewAdapter extends RecyclerView.Adapter<ContactsViewAdapter.ViewHolder>{

    private ArrayList<Contact> contacts = new ArrayList<>();

    private Context context;

    private Activity mainActivity;
    MainActivity mainActivityInstance;

    private View mContentView;

    //private ActivitySwitcher activitySwitcher;

    public ContactsViewAdapter(Context context, Activity activity, MainActivity mainActivityInstance) {

        this.context = context;
        this.mainActivity = activity;
        this.mainActivityInstance = mainActivityInstance;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //                                                                                        wodran soll view attached sein, false weil true reduntant null wenn man sich nicht sicher ist wodran view attached ist
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item, parent, false);

        //ViewHolder holder = new ViewHolder(view);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(contacts.get(position).getName());       //hier kann mehr hinzugefügt werden
        //holder.txtEmail.setText(contacts.get(position).getEmail());

        //TODO: Herausfinden warum manche der PlaceHolder light werden.
        if(contacts.get(position).getLightMode()){
            holder.txtName.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.image.setImageAlpha(255); //setAlpha()
        }

        /*
        holder.parent.setOnClickListener(new View.OnClickListener(){;
            @Override
            public void onClick(View v){

                mainActivityInstance.switchActivity(universalMenu.class, holder.txtName.getText().toString());
            }
        });*/
        //Das selbe aber vereinfacht
        holder.parent.setOnClickListener((View v) -> mainActivityInstance.switchActivity(universalMenu.class, holder.txtName.getText().toString()));

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
