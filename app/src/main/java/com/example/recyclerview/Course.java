package com.example.recyclerview;

import android.graphics.Color;

public class Course {
    private String course;
    private String teacher;
    private String room;
    private int colorBack;
    private int colorCourse;

    public int getColorBack() {
        return colorBack;
    }

    public void setColorBack(int colorBack) {
        this.colorBack = colorBack;
    }

    public int getColorCourse() {
        return colorCourse;
    }

    public void setColorCourse(int colorCourse) {
        this.colorCourse = colorCourse;
    }

    public Course(String course, String teacher, String room, int colorBack, int colorCourse) {
        this.course = course;
        this.teacher = teacher;
        this.room = room;
        this.colorBack = colorBack;
        this.colorCourse = colorCourse;
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
}
