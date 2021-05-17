package com.hlgkaifu.recyclerview;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeworkEditBottomSheetDialog extends BottomSheetDialogFragment {

    FragmentManager fragmentManager;
    HomeworkViewAdapter adapter;
    HomeworkActivity homeworkActivityInstance;
    Homework homework;
    String dateToSet,subjectToSet,extraInfToSet;
    int positionToEdit;

    HomeworkEditBottomSheetDialog(FragmentManager fragmentManager, HomeworkViewAdapter adapter, HomeworkActivity homeworkActivityInstance, Homework homework, int position){
        this.fragmentManager = fragmentManager;
        this.adapter = adapter;
        this.homeworkActivityInstance = homeworkActivityInstance;
        this.homework = homework;
        this.positionToEdit = position;
    }

    Context context;
    String dateStr;
    int fragmentId;
    private String[] classes = NotenrechnerViewAdapter.fächer;
    public EditText date, extraInf, subject;
    HomeworkEditBottomSheetDialog thisFragment;
    Button doneBtn;
    RecyclerView homeworkRecyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RelativeLayout dateBox;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        thisFragment = this;

        sharedPreferences = homeworkActivityInstance.getSharedPreferences(getString(R.string.mainPreferenceKey),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        View v = inflater.inflate(R.layout.bottomsheet_homework,container,false);


        AutoCompleteTextView addClassField = v.findViewById(R.id.fachTxt);

        date = v.findViewById(R.id.date);
        doneBtn = v.findViewById(R.id.doneBtn);
        extraInf = v.findViewById(R.id.extInfTxt);
        subject = v.findViewById(R.id.fachTxt);
        dateBox = v.findViewById(R.id.dateBox);


        date.setText(homework.getDateStr());
        subject.setText(homework.getSubject());
        extraInf.setText(homework.getExtraInfo());
        homework.setDate(Homework.convertToDate(homework.getDateStr()));


        ArrayAdapter<String> adapterFach = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,classes);
        addClassField.setAdapter(adapterFach);
        System.out.println("Fächer: "+classes);

        fragmentId = this.getId();

        dateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DatePickerFragment newFragment = new DatePickerFragment(fragmentManager, fragmentId);
                DatePickerFragment newFragment = new DatePickerFragment(thisFragment);
                newFragment.show(fragmentManager,"datePicker");

            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subject.getText().toString()!=""||extraInf.getText().toString()!=""||date.getText().toString()!=""){

                    homework.setSubject(subject.getText().toString());
                    homework.setExtraInfo(extraInf.getText().toString());


                    Homework.homeworkList.set(positionToEdit,homework);

                    adapter.updateHomework();

                    adapter.notifyItemInserted(adapter.getItemCount());
                    adapter.sort(Homework.homeworkList,homeworkActivityInstance.getString(R.string.homeworkPreferenceKey));
                    adapter.updateHomework();
                    adapter.notifyDataSetChanged();



                    setList(getString(R.string.homeworkPreferenceKey),Homework.homeworkList);


                    //getActivity().onBackPressed();
                    //getActivity().getFragmentManager().beginTransaction().remove(fragment).commit();
                    dismiss();
                    

                }
                else{
                    Toast.makeText(context,"Mindestens ein Feld muss ausgefüllt sein",Toast.LENGTH_SHORT);
                }
            }
        });


        return v;
    }


    public <T> void setList(String key, ArrayList<T> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println("----------------"+json);
        set(key,json);
    }
    public void set(String key, String value){
        editor.putString(key,value);
        editor.apply();
    }

}
