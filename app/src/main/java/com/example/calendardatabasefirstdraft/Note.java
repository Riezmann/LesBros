package com.example.calendardatabasefirstdraft;

public class Note {
    int noteID;
    String currentDate;
    String text;

    public Note() {
    }

    public Note(String currentDate, String text) {
        this.currentDate = currentDate;
        this.text = text;
    }

    public Note(int noteID, String currentDate, String text) {
        this.noteID = noteID;
        this.currentDate = currentDate;
        this.text = text;
    }

    @Override
    public String toString() {
        return "" + text;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
