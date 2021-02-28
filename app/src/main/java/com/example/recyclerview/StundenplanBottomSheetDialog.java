package com.example.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager2.widget.ViewPager2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class StundenplanBottomSheetDialog extends BottomSheetDialogFragment {

    Context context;
    ViewPager2 viewPager2;
    CircleIndicator3 circleIndicator3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();

        View v = inflater.inflate(R.layout.viewpage_stundenplan, container, false);  //stundenplan_bottom_sheet

        viewPager2 = (ViewPager2) v.findViewById(R.id.viewPagerStudenplan);
        List<String> list = new ArrayList<>();
        list.add("First Page");
        list.add("Second Page");
        list.add("Third Page");
        viewPager2.setAdapter(new StundenplanViewPagerAdapter(context, list, viewPager2));

        circleIndicator3 = (CircleIndicator3) v.findViewById(R.id.indicator);
        circleIndicator3.setViewPager(viewPager2);
        return v;

    }

}
