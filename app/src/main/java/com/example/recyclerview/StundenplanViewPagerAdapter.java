package com.example.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class StundenplanViewPagerAdapter extends RecyclerView.Adapter<StundenplanViewPagerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> text;
    private ViewPager2 viewPager2;

    StundenplanViewPagerAdapter(Context context, List<String> text, ViewPager2 viewPager2){
        this.inflater = LayoutInflater.from(context);
        this.text = text;
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
        holder.textView.setText(text.get(position));
    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        RelativeLayout relativeLayout;

        ViewHolder(View item){
            super(item);
            textView = item.findViewById((R.id.viewpagertext));
            relativeLayout = item.findViewById(R.id.container);

        }



    }


}

