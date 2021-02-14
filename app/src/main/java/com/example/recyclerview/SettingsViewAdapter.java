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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettingsViewAdapter extends RecyclerView.Adapter<SettingsViewAdapter.ViewHolder> {

    private ArrayList<SettingsProperty> settings = new ArrayList<>();

    private Context context;

    private Activity mainActivity;
    profile_page mainActivityInstance;

    public SettingsViewAdapter(Context context, Activity activity, profile_page mainActivityInstance) {
        this.context = context;
        this.mainActivity = activity;
        this.mainActivityInstance = mainActivityInstance;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_list_item,parent,false);
        return new SettingsViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.toggle.setActivated(settings.get(position).isToggleable());

        //holder.txtValue.setActivated(!settings.get(position).isToggleable());

        if(settings.get(position).isToggleable()){
            holder.txtValue.setVisibility(View.GONE);
            if(settings.get(position).isActive()){
                holder.toggle.setColorFilter(context.getColor(R.color.toggleActive));
            }else{
                holder.toggle.setColorFilter(context.getColor(R.color.toggleNotActive));
            }
            //holder.toggle.setColorFilter(R.color.active_icon);
            //holder.toggle.setColorFilter(context.getColor(R.color.active_icon));
        }else{
            holder.toggle.setVisibility(View.GONE);
        }
        holder.txtValue.setText(settings.get(position).getValue());
        holder.txtProperty.setText(settings.get(position).getProperty());
    }

    public void setSettings(ArrayList<SettingsProperty> settings){
        this.settings = settings;
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtProperty, txtValue; //txtEmail;
        private ImageView toggle;
        private CardView parent;


        //private News newsObj;
        private RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.parentRelative);
            parent = itemView.findViewById(R.id.parent);

            txtProperty = itemView.findViewById(R.id.txtProperty);
            txtValue = itemView.findViewById(R.id.txtValue);
            toggle = itemView.findViewById(R.id.toggle);



        }
    }
}
