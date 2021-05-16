package com.hlgkaifu.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.hlgkaifu.recyclerview.Course;
import com.hlgkaifu.recyclerview.StundenplanViewAdapter;

import java.util.ArrayList;

public class StundenplanViewPagerAdapter extends RecyclerView.Adapter<StundenplanViewPagerAdapter.ViewHolder> {

    private LayoutInflater inflater;

    private ViewPager2 viewPager2;
    private ArrayList<ArrayList<Course>> listRecview;
    private ArrayList<Course> courses;
    private StundenplanViewAdapter adapter;
    private Context context;
    private String day;

    StundenplanViewPagerAdapter(Context context, ViewPager2 viewPager2){
        this.inflater = LayoutInflater.from(context);
        this.context = context;

        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.stundenplan_item_viewpage, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtDay.setText(listRecview.get(position).get(0).getDay());
        adapter = new StundenplanViewAdapter(context);
        adapter.setCourses(listRecview.get(position));
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public int getItemCount() {

        try {
            return listRecview.size();
        }catch(NullPointerException e){
            System.out.println("---------Exception:" + e);
        }
        return 0;
    }

    public void setRecyclerViews(ArrayList<ArrayList<Course>> listRecview) {
        this.listRecview = listRecview;
        notifyDataSetChanged(); //wichtig damit die Daten sich aktualisieren
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        RelativeLayout relativeLayout;
        private TextView txtDay;

        ViewHolder(View item){
            super(item);
            recyclerView = item.findViewById((R.id.studenplanRecview));
            relativeLayout = item.findViewById(R.id.container);
            txtDay = item.findViewById(R.id.day);


        }
    }


}

