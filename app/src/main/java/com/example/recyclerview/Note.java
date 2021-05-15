package com.hlgkaifu.recyclerview;

public class Note {

    private String Fach;
    private String Note;

    public Note(String fach, String note) {
        Fach = fach;
        Note = note;
    }

    public String getFach() {
        return Fach;
    }

    public void setFach(String fach) {
        Fach = fach;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
