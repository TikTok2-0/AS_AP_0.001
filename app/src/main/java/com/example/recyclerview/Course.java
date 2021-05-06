package com.example.recyclerview;

import android.widget.ImageView;

public class Course {
    private String course;
    private String teacher;
    private String room;
    private String status;

    private String day;

    public Course(String course, String teacher, String room, String status, String day) {


        this.course = course;
        this.teacher = teacher;
        this.room = room;
        this.status = status;

        this.day = day;

    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
