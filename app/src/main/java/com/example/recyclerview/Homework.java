package com.example.recyclerview;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Homework {

    private Date date;
    private String subject;
    private String extraInfo;
    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDateStr() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(date);

    }
    public Date getDate(){
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public static ArrayList<Homework>   homeworkList = new ArrayList<>(),
                                        activeHomwork = new ArrayList<>(),
                                        completedHomework = new ArrayList<>();

    Homework(Date date, String subject, String extraInfo){
        this.date = date;
        this.subject = subject;
        this.extraInfo = extraInfo;
    }
    Homework(){}
    public static void addToList(Homework homework){
        if (homeworkList == null){
            homeworkList = new ArrayList<>();
        }
        homeworkList.add(homework);
        activeHomwork.add(homework);
    }

    public static Date convertToDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date d = null;
        try{
        d = format.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return d;
    }
    public static String Date2String(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return  format.format(date);
    }

    public static void updateActiveHomework(){
        activeHomwork.clear();
        for(int i=0;i<homeworkList.size();i++){
            if(!homeworkList.get(i).isCompleted()){
                activeHomwork.add(homeworkList.get(i));
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        try {
            return ("subject: " + subject + "\ndate: " + Date2String(date) + "\nextra: " + extraInfo + "\nisActive: " + completed);
        }catch (Exception e){
            return ("No full");
        }
    }

}
