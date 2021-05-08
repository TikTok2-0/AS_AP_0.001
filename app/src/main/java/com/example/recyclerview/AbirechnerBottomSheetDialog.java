package com.example.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager2.widget.ViewPager2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class AbirechnerBottomSheetDialog extends BottomSheetDialogFragment {

    public static AbirechnerBottomSheetDialog newInstance() {
        return new AbirechnerBottomSheetDialog();
    }


    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();

        View v = inflater.inflate(R.layout.bottomsheet_studenplan, container, false);  //stundenplan_bottom_sheet



        return v;

    }

}
