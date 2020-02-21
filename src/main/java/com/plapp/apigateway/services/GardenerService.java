package com.plapp.apigateway.services;

import com.plapp.entities.schedules.ScheduleAction;

import java.util.List;

public interface GardenerService {
    List<ScheduleAction> getSchedule(long plantId) throws Exception;
    List<String> getActions() throws Exception;
    void addScheduleAction(ScheduleAction scheduleAction) throws Exception;
}
