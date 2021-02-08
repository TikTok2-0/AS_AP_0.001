package com.example.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.ViewHolder>{

    private ArrayList<News> news = new ArrayList<>();

    private News newsObj;

    private Context context;

    private Activity mainActivity;
    newsActivity mainActivityInstance;

    private View mContentView;

    //private ActivitySwitcher activitySwitcher;

    public NewsViewAdapter(Context context, Activity activity, newsActivity mainActivityInstance) {

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
        holder.txtName.setText(news.get(position).getTitle());       //hier kann mehr hinzugefügt werden

        //TODO: Herausfinden warum manche der PlaceHolder light werden.

        /*if(news.get(position).getLightMode()){
            holder.txtName.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.image.setImageAlpha(255); //setAlpha()
        }*/

        /*
        holder.parent.setOnClickListener(new View.OnClickListener(){;
            @Override
            public void onClick(View v){

                mainActivityInstance.switchActivity(universalMenu.class, holder.txtName.getText().toString());
            }
        });*/



        //Das selbe aber vereinfacht
        //TODO andere News Variabeln einbauen und News page hinzufügen
        holder.parent.setOnClickListener((View v) -> mainActivityInstance.switchActivity(universalMenu.class, position));

        //loading images
        Glide.with(context)
                .asBitmap()
                .load(news.get(position).getImageUrl())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
        notifyDataSetChanged(); //wichtig damit die Daten sich aktualisieren
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName; //txtEmail;
        private CardView parent;
        private ImageView image;
        //private News newsObj;
        private ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            parent = itemView.findViewById(R.id.parent);
            constraintLayout = itemView.findViewById(R.id.parentContraint);
            /*
            for(News singleNews : news){
                if(txtName.toString().equals(singleNews.getCaption().toString())){
                    newsObj = singleNews;
                }
            }*/
            //txtEmail = itemView.findViewById(R.id.txtEmail);

            image = itemView.findViewById(R.id.image);
        }
    }
}
