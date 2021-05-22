package com.example.calendardatabasefirstdraft;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCalendar {
    int dateID;
    int weekID;
    int weekOrder;
    String day_of_week;
    String currentDate;

    @Override
    public String toString() {
        return currentDate;
    }

    public DateCalendar() {
    }

    public DateCalendar(int weekID, int weekOrder, String day_of_week, String currentDate) {
        this.weekID = weekID;
        this.day_of_week = day_of_week;
        this.currentDate = currentDate;
        this.weekOrder = weekOrder;
    }

    public DateCalendar(int dateID, int weekID, int weekOrder, String day_of_week, Date currentDate) {
        this.dateID = dateID;
        this.weekID = weekID;
        this.day_of_week = day_of_week;
        this.currentDate = new SimpleDateFormat("dd/MM/yyyy").format(currentDate);
        this.weekOrder =weekOrder;
    }

    public int getDateID() {
        return dateID;
    }

    public void setDateID(int dateID) {
        this.dateID = dateID;
    }

    public int getWeekID() {
        return weekID;
    }

    public void setWeekID(int weekID) {
        this.weekID = weekID;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public String getCurrentDate() {
        return this.currentDate;
    }
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }


    public int getWeekOrder() {
        return weekOrder;
    }

    public void setWeekOrder(int weekOrder) {
        this.weekOrder = weekOrder;
    }
}
