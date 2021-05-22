package com.example.calendardatabasefirstdraft;

public class weekCalendar {
    int weekID;
    int order_week;

    public weekCalendar() {
    }

    public weekCalendar(int weekID, int order_week) {
        this.weekID = weekID;
        this.order_week = order_week;
    }

    public weekCalendar(int order_week) {
        this.order_week = order_week;
    }

    public int getWeekID() {
        return weekID;
    }

    public void setWeekID(int weekID) {
        this.weekID = weekID;
    }

    public int getOrder_week() {
        return order_week;
    }

    public void setOrder_week(int order_week) {
        this.order_week = order_week;
    }
}
