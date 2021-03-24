package com.example.recyclerview;

import android.app.Fragment;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class HomeworkBottomSheetDialog extends BottomSheetDialogFragment {

    /*public static HomeworkBottomSheetDialog newInstance(){
        return new HomeworkBottomSheetDialog();
    }*/

    FragmentManager fragmentManager;
    HomeworkViewAdapter adapter;
    homeworkActivity homeworkActivityInstance;
    boolean usedAsEditor;
    String dateToSet,subjectToSet,extraInfToSet;
    int positionToEdit;

    HomeworkBottomSheetDialog(FragmentManager fragmentManager, HomeworkViewAdapter adapter, homeworkActivity homeworkActivityInstance, boolean usedAsEditor){
        this.fragmentManager = fragmentManager;
        this.adapter = adapter;
        this.homeworkActivityInstance = homeworkActivityInstance;
        this.usedAsEditor = usedAsEditor;
    }

    public HomeworkBottomSheetDialog(FragmentManager fragmentManager, HomeworkViewAdapter adapter,
                                     homeworkActivity homeworkActivityInstance, boolean usedAsEditor,
                                     String dateToSet, String subjectToSet, String extraInfToSet, int positionToEdit) {
        this.fragmentManager = fragmentManager;
        this.adapter = adapter;
        this.homeworkActivityInstance = homeworkActivityInstance;
        this.usedAsEditor = usedAsEditor;
        this.dateToSet = dateToSet;
        this.subjectToSet = subjectToSet;
        this.extraInfToSet = extraInfToSet;
        this.positionToEdit = positionToEdit;
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

        homework = new Homework();

        AutoCompleteTextView addClassField = v.findViewById(R.id.fachTxt);

        date = v.findViewById(R.id.date);
        doneBtn = v.findViewById(R.id.doneBtn);
        extraInf = v.findViewById(R.id.extInfTxt);
        subject = v.findViewById(R.id.fachTxt);
        dateBox = v.findViewById(R.id.dateBox);

        if(usedAsEditor){
            date.setText(dateToSet);
            subject.setText(subjectToSet);
            extraInf.setText(extraInfToSet);
            homework.setDate(Homework.convertToDate(dateToSet));
        }

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
        dateBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                homework.setDate(null);
                date.setText("");
                return true;
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subject.getText().toString()!=""||extraInf.getText().toString()!=""||date.getText().toString()!=""){

                    homework.setSubject(subject.getText().toString());
                    homework.setExtraInfo(extraInf.getText().toString());

                    System.out.println("-------------------setting homework: "+subject.getText().toString()+", "+extraInf.getText().toString());
                    if(!usedAsEditor) {
                        Homework.addToList(homework);
                    }else{

                        Homework.homeworkList.set(positionToEdit,homework);
                    }
                    adapter.updateHomework();
                    adapter.notifyItemInserted(adapter.getItemCount());
                    adapter.sort();
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
