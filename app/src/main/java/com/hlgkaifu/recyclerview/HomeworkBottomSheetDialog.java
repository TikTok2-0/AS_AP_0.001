package com.hlgkaifu.recyclerview;

import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hlgkaifu.recyclerview.Homework;
import com.hlgkaifu.recyclerview.HomeworkViewAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeworkBottomSheetDialog extends BottomSheetDialogFragment {

    /*public static HomeworkBottomSheetDialog newInstance(){
        return new HomeworkBottomSheetDialog();
    }*/

    FragmentManager fragmentManager;
    HomeworkViewAdapter adapter;
    com.hlgkaifu.recyclerview.HomeworkActivity homeworkActivityInstance;
    boolean usedAsEditor;
    String dateToSet,subjectToSet,extraInfToSet;
    int positionToEdit;

    HomeworkBottomSheetDialog(FragmentManager fragmentManager, HomeworkViewAdapter adapter, com.hlgkaifu.recyclerview.HomeworkActivity homeworkActivityInstance, boolean usedAsEditor){
        this.fragmentManager = fragmentManager;
        this.adapter = adapter;
        this.homeworkActivityInstance = homeworkActivityInstance;
        this.usedAsEditor = usedAsEditor;
        wasClickedTime = false;
    }

    public HomeworkBottomSheetDialog(FragmentManager fragmentManager, HomeworkViewAdapter adapter,
                                     com.hlgkaifu.recyclerview.HomeworkActivity homeworkActivityInstance, boolean usedAsEditor,
                                     String dateToSet, String subjectToSet, String extraInfToSet, int positionToEdit) {
        this.fragmentManager = fragmentManager;
        this.adapter = adapter;
        this.homeworkActivityInstance = homeworkActivityInstance;
        this.usedAsEditor = usedAsEditor;
        this.dateToSet = dateToSet;
        this.subjectToSet = subjectToSet;
        this.extraInfToSet = extraInfToSet;
        this.positionToEdit = positionToEdit;
        wasClickedTime = false;
    }

    Context context;
    String dateStr;
    int fragmentId;
    private String[] classes = com.hlgkaifu.recyclerview.NotenrechnerViewAdapter.fächer;
    public EditText date, extraInf, subject, time;
    HomeworkBottomSheetDialog thisFragment;
    public Homework homework;
    Button doneBtn;
    RecyclerView homeworkRecyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RelativeLayout dateBox, timeBox;
    private boolean wasClickedTime;


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
        timeBox = v.findViewById(R.id.timeBox);
        time = v.findViewById(R.id.time);

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
                com.hlgkaifu.recyclerview.DatePickerFragment newFragment = new com.hlgkaifu.recyclerview.DatePickerFragment(thisFragment);
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


        //TODO Zeit fertig machen
        timeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mHour, mMin;
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMin = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(homeworkActivityInstance, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        homework.setTimeHour(hourOfDay);
                        homework.setTimeMin(minute);
                        time.setText(formatTime(homework.getTimeHour())+":"+formatTime(homework.getTimeMin()));
                        wasClickedTime = true;
                    }
                }, mHour, mMin, true);
                timePickerDialog.show();
            }
        });



        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!subject.getText().toString().equals("") && !date.getText().toString().equals("") && wasClickedTime){

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
                    adapter.sort(Homework.homeworkList,homeworkActivityInstance.getString(R.string.homeworkPreferenceKey));
                    adapter.updateHomework();
                    adapter.notifyDataSetChanged();



                    setList(getString(R.string.homeworkPreferenceKey),Homework.homeworkList);


                    //getActivity().onBackPressed();
                    //getActivity().getFragmentManager().beginTransaction().remove(fragment).commit();
                    dismiss();
                    

                }
                else{
                    String unanswered = "";
                    if (subject.getText().toString().equals("")){
                        unanswered = unanswered + "Courses";
                    }
                    if (date.getText().toString().equals("")){
                        if (unanswered.equals("")){
                            unanswered = unanswered + "Date";
                        }else{
                            unanswered = unanswered +  ", date";
                        }
                    }
                    if (!wasClickedTime){
                        if (unanswered.equals("")){
                            unanswered = unanswered + "Time";
                        }else{
                            unanswered = unanswered +  ", time";
                        }
                    }
                    unanswered = unanswered +  " must be filled";


                    Toast.makeText(context,unanswered,Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }

    private String formatTime(int time){
        String formatedTime = String.valueOf(time);
        if(time<10){
            formatedTime = "0"+formatedTime;
        }
        return formatedTime;
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
