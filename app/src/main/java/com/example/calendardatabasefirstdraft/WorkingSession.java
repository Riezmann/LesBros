package com.example.calendardatabasefirstdraft;

public class WorkingSession {
    int workingSessionID;
    String name;
    int weekID;
    int dateID;
    int tagID;
    String Start_time;
    String End_time;
    boolean isOnTime;
    boolean status;

    @Override
    public String toString() {
        return "" + name + " start from " + Start_time + " to " + End_time + ", Done: " + status;
    }

    public WorkingSession() {

    }

    public WorkingSession(String name, int weekID, int dateID, int tagID, String start_time, String end_time,boolean status, boolean isOnTime) {
        this.name = name;
        this.weekID = weekID;
        this.dateID = dateID;
        this.tagID = tagID;
        Start_time = start_time;
        End_time = end_time;
        this.isOnTime = isOnTime;
        this.status = status;
    }

    public WorkingSession(int workingSessionID, String name, int weekID, int dateID, int tagID, String start_time, String end_time, boolean status, boolean isOnTime) {
        this.workingSessionID = workingSessionID;
        this.name = name;
        this.weekID = weekID;
        this.dateID = dateID;
        this.tagID = tagID;
        Start_time = start_time;
        End_time = end_time;
        this.isOnTime = isOnTime;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkingSessionID() {
        return workingSessionID;
    }

    public void setWorkingSessionID(int workingSessionID) {
        this.workingSessionID = workingSessionID;
    }

    public int getWeekID() {
        return weekID;
    }

    public void setWeekID(int weekID) {
        this.weekID = weekID;
    }

    public int getDateID() {
        return dateID;
    }

    public void setDateID(int dateID) {
        this.dateID = dateID;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public String getStart_time() {
        return Start_time;
    }



    public void setStart_time(String start_time) {
        Start_time = start_time;
    }

    public String getEnd_time() {
        return End_time;
    }

    public void setEnd_time(String end_time) {
        End_time = end_time;
    }

    public boolean isOnTime() {
        return isOnTime;
    }

    public void setOnTime(boolean onTime) {
        isOnTime = onTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}


