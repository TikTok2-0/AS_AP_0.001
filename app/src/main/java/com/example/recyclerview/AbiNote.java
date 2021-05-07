package com.example.recyclerview;

import java.util.ArrayList;

public class AbiNote {

    private String course;
    private ArrayList SemesterGrade;
    private boolean isExam;
    private int examGrade;
    private boolean isHigherLevel;

    public AbiNote(String course, ArrayList semesterGrade, boolean isExam, int examGrade, boolean isHigherLevel) {
        this.course = course;
        SemesterGrade = semesterGrade;
        this.isExam = isExam;
        this.examGrade = examGrade;
        this.isHigherLevel = isHigherLevel;
    }

    public String getCourse() { return course; }

    public void setCourse(String course) { this.course = course; }

    public ArrayList getSemesterGrade() { return SemesterGrade; }

    public void setSemesterGrade(ArrayList semesterGrade) { SemesterGrade = semesterGrade; }

    public boolean isExam() { return isExam; }

    public void setExam(boolean exam) { isExam = exam; }

    public int getExamGrade() { return examGrade; }

    public void setExamGrade(int examGrade) { this.examGrade = examGrade; }

    public boolean isHigherLevel() { return isHigherLevel; }

    public void setHigherLevel(boolean higherLevel) { isHigherLevel = higherLevel; }
}
