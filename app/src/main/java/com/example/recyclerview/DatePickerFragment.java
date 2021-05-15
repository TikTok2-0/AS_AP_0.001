package com.hlgkaifu.recyclerview;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.hlgkaifu.recyclerview.Homework;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
                                implements DatePickerDialog.OnDateSetListener {

    String currentDate;


    int fragmentId;
    FragmentManager fragmentManager;

    DatePickerFragment(FragmentManager fragmentManager, int fragmentId){
        this.fragmentManager = fragmentManager;
        this.fragmentId = fragmentId;
    }

    com.hlgkaifu.recyclerview.HomeworkBottomSheetDialog homeworkBottomSheetDialog;
    DatePickerFragment(com.hlgkaifu.recyclerview.HomeworkBottomSheetDialog homeworkBottomSheetDialog){
        this.homeworkBottomSheetDialog = homeworkBottomSheetDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this, year, month,day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //System.out.println("--------------------Date: "+ ("0"+(month+1)));

        currentDate = ("0"+dayOfMonth).substring(String.valueOf(dayOfMonth).length()-1)
                        +"."+
                    ("0"+(month+1)).substring(String.valueOf(month).length()-1) +"."+
                year;
                //("0"+year).substring(String.valueOf(year).length()-1);

        //EditText dateEdttxt = fragmentManager.findFragmentById(fragmentId).getActivity().findViewById(R.id.date);
        EditText dateEdttxt = homeworkBottomSheetDialog.date;
        System.out.println("------------------Date---: "+currentDate);

        dateEdttxt.setText(currentDate);

        Homework homework = homeworkBottomSheetDialog.homework;

        homework.setDate(Homework.convertToDate(currentDate));

        //System.out.println("--Converted date--"+Homework.convertToDate(currentDate));
        //homeworkBottomSheetDialog.date.setText(currentDate);

    }
    public String getDate(){
        return currentDate;
    }
}
