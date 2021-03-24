package com.example.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class HomeworkBottomSheetDialog extends BottomSheetDialogFragment {

    /*public static HomeworkBottomSheetDialog newInstance(){
        return new HomeworkBottomSheetDialog();
    }*/

    FragmentManager fragmentManager;
    HomeworkViewAdapter adapter;

    HomeworkBottomSheetDialog(FragmentManager fragmentManager, HomeworkViewAdapter adapter){
        this.fragmentManager = fragmentManager;
        this.adapter = adapter;

    }

    Context context;
    String dateStr;
    int fragmentId;
    private String[] classes = NotenrechnerViewAdapter.fächer;
    public EditText date, extraInf, subject;
    HomeworkBottomSheetDialog thisFragment;
    public Homework homework;
    Button doneBtn;
    RecyclerView homeworkRecyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        thisFragment = this;

        View v = inflater.inflate(R.layout.bottomsheet_homework,container,false);

        homework = new Homework();

        AutoCompleteTextView addClassField = v.findViewById(R.id.fachTxt);

        date = v.findViewById(R.id.date);
        doneBtn = v.findViewById(R.id.doneBtn);
        extraInf = v.findViewById(R.id.extInfTxt);
        subject = v.findViewById(R.id.fachTxt);

        ArrayAdapter<String> adapterFach = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,classes);
        addClassField.setAdapter(adapterFach);
        System.out.println("Fächer: "+classes);

        fragmentId = this.getId();
        //Fragment fragment = this;
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DatePickerFragment newFragment = new DatePickerFragment(fragmentManager, fragmentId);
                DatePickerFragment newFragment = new DatePickerFragment(thisFragment);
                newFragment.show(fragmentManager,"datePicker");
                //getActivity().getFragmentManager().beginTransaction().remove(fragment).commit();

                //date.setText(newFragment.getDate());
                //dateStr = newFragment.getDate();

            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subject.getText().toString()!=""||extraInf.getText().toString()!=""||date.getText().toString()!=""){
                    homework.setSubject(subject.getText().toString());
                    homework.setExtraInfo(extraInf.getText().toString());
                    System.out.println("-------------------setting homework: "+subject.getText().toString()+", "+extraInf.getText().toString());
                    Homework.addToList(homework);
                    adapter.updateHomework();
                    adapter.notifyItemInserted(adapter.getItemCount());

                    getActivity().onBackPressed();

                }
                else{
                    Toast.makeText(context,"Mindestens ein Feld muss ausgefüllt sein",Toast.LENGTH_SHORT);
                }
            }
        });


        return v;
    }

}
