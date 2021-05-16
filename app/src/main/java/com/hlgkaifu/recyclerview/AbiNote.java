package com.hlgkaifu.recyclerview;

import java.util.ArrayList;

public class AbiNote {

    private String course;
    private int pointsSem1;
    private int pointsSem2;
    private int pointsSem3;
    private int pointsSem4;
    private boolean isExam;
    private int examGrade;
    private boolean isHigherLevel;

    public AbiNote(String course, int pointsSem1, int pointsSem2, int pointsSem3, int pointsSem4, boolean isExam, int examGrade, boolean isHigherLevel) {
        this.course = course;
        this.pointsSem1 = pointsSem1;
        this.pointsSem2 = pointsSem2;
        this.pointsSem3 = pointsSem3;
        this.pointsSem4 = pointsSem4;
        this.isExam = isExam;
        this.examGrade = examGrade;
        this.isHigherLevel = isHigherLevel;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getPointsSem1() {
        return pointsSem1;
    }

    public void setPointsSem1(int pointsSem1) {
        this.pointsSem1 = pointsSem1;
    }

    public int getPointsSem2() {
        return pointsSem2;
    }

    public void setPointsSem2(int pointsSem2) {
        this.pointsSem2 = pointsSem2;
    }

    public int getPointsSem3() {
        return pointsSem3;
    }

    public void setPointsSem3(int pointsSem3) {
        this.pointsSem3 = pointsSem3;
    }

    public int getPointsSem4() {
        return pointsSem4;
    }

    public void setPointsSem4(int pointsSem4) {
        this.pointsSem4 = pointsSem4;
    }

    public boolean isExam() {
        return isExam;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }

    public int getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(int examGrade) {
        this.examGrade = examGrade;
    }

    public boolean isHigherLevel() {
        return isHigherLevel;
    }

    public void setHigherLevel(boolean higherLevel) {
        isHigherLevel = higherLevel;
    }
}
