package com.plapp.apigateway.services;

import com.plapp.entities.schedules.ScheduleAction;

import java.util.List;

public interface GardenerService {
    List<ScheduleAction> getSchedule(long plantId);
    List<String> getActions();
    void addScheduleAction(ScheduleAction scheduleAction);
}
