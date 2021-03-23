package com.example.recyclerview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.viewpager2.widget.ViewPager2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class StundenplanBottomSheetDialog extends BottomSheetDialogFragment {

    public static StundenplanBottomSheetDialog newInstance() {
        return new StundenplanBottomSheetDialog();
    }

    Context context;
    ViewPager2 viewPager2;
    CircleIndicator3 circleIndicator3;
    StundenplanViewPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();

        View v = inflater.inflate(R.layout.bottomsheet_studenplan, container, false);  //stundenplan_bottom_sheet

        viewPager2 = (ViewPager2) v.findViewById(R.id.viewPagerStudenplan);
        List<String> list = new ArrayList<>();
        list.add("First Page");
        list.add("Second Page");
        list.add("Third Page");

        ArrayList<CourseVP> monday = new ArrayList<>();
        monday.add(new CourseVP("Mathe", "Ho", "Raum 205", "Unterricht", "Montag"));
        monday.add(new CourseVP("Englisch", "vB", "Raum 306", "Vertretung", "Montag"));
        monday.add(new CourseVP("Deutsch", "Ve", "Raum 205", "Entfall", "Montag"));
        monday.add(new CourseVP("Physik", "Dn", "Raum 306", "Unterricht", "Montag"));

        ArrayList<CourseVP> tuesday = new ArrayList<>();
        tuesday.add(new CourseVP("Physik", "Dn", "Raum 306", "Unterricht", "Dienstag"));
        tuesday.add(new CourseVP("Deutsch", "Ve", "Raum 205", "Entfall", "Dienstag"));
        tuesday.add(new CourseVP("Englisch", "vB", "Raum 306", "Vertretung", "Dienstag"));
        tuesday.add(new CourseVP("Mathe", "Ho", "Raum 205", "Unterricht", "Dienstag"));

        ArrayList<ArrayList<CourseVP>> listRecview = new ArrayList<>();
        listRecview.add(monday);
        listRecview.add(tuesday);

        adapter = new StundenplanViewPagerAdapter(context, viewPager2);
        adapter.setRecyclerViews(listRecview);

        viewPager2.setAdapter(adapter);

        circleIndicator3 = (CircleIndicator3) v.findViewById(R.id.indicator);
        circleIndicator3.setViewPager(viewPager2);
        return v;

    }


}

