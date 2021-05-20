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
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

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
        wasClickedTime = false;
    }

    Context context;
    String dateStr;
    int fragmentId;
    private String[] classes = NotenrechnerViewAdapter.fächer;
    public EditText date, extraInf, subject, time;
    HomeworkEditBottomSheetDialog thisFragment;
    Button doneBtn;
    RecyclerView homeworkRecyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RelativeLayout dateBox, timeBox;
    boolean wasClickedTime;
    Switch switchWeek, switchHour, switchDay;



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
        timeBox = v.findViewById(R.id.timeBox);
        time = v.findViewById(R.id.time);
        switchWeek = v.findViewById(R.id.switchNotWeek);
        switchHour = v.findViewById(R.id.switchNotHour);
        switchDay = v.findViewById(R.id.switchNotDay);

        date.setText(homework.getDateStr());
        subject.setText(homework.getSubject());
        extraInf.setText(homework.getExtraInfo());
        homework.setDate(Homework.convertToDate(homework.getDateStr()));
        time.setText(formatTime(homework.getTimeHour())+":"+formatTime(homework.getTimeMin()));
        switch(homework.getNotification()){
            case "week":
                switchWeek.setChecked(true);
                break;
            case "day":
                switchDay.setChecked(true);
                break;
            case "hour":
                switchHour.setChecked(true);
                break;
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

        timeBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mHour, mMin;
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMin = c.get(Calendar.MINUTE);
                wasClickedTime = true;

                TimePickerDialog timePickerDialog = new TimePickerDialog(homeworkActivityInstance, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        homework.setTimeHour(hourOfDay);
                        homework.setTimeMin(minute);
                        time.setText(formatTime(homework.getTimeHour())+":"+formatTime(homework.getTimeMin()));
                    }
                }, mHour, mMin, true);
                timePickerDialog.show();
            }
        });

        switchDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchDay.isChecked()){
                    switchHour.setChecked(false);
                    switchWeek.setChecked(false);
                }
            }
        });

        switchHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchHour.isChecked()){
                    switchDay.setChecked(false);
                    switchWeek.setChecked(false);
                }
            }
        });

        switchWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchWeek.isChecked()){
                    switchDay.setChecked(false);
                    switchHour.setChecked(false);
                }
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!subject.getText().toString().equals("") && !date.getText().toString().equals("")){

                    homework.setSubject(subject.getText().toString());
                    homework.setExtraInfo(extraInf.getText().toString());

                    if(switchHour.isChecked()){
                        homework.setNotification("hour");
                    }else if(switchDay.isChecked()){
                        homework.setNotification("day");
                    }else if(switchWeek.isChecked()){
                        homework.setNotification("week");
                    }


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
