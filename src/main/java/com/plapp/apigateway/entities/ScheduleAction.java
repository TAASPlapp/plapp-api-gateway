package com.plapp.apigateway.entities;

import java.util.Date;


public class ScheduleAction {
    private long plantID;
    private String action;
    private Date date;
    private int periodicity;


    public ScheduleAction(long plantID, Date date, String action, int periodicity) {
        this.plantID = plantID;
        this.date = date;
        this.action = action;
        this.periodicity = periodicity;
    }

    public long getPlantID() {
        return plantID;
    }

    public Date getDate() {
        return date;
    }

    public String getAction() {
        return action;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                ", plantID=" + plantID +
                ", date=" + date +
                ", action='" + action +
                ", periodicity='" + periodicity + '\'' +
                '}';
    }
}
