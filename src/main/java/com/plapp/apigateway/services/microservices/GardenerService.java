package com.plapp.apigateway.services.microservices;

import com.plapp.entities.schedules.ScheduleAction;

import java.util.List;

public interface GardenerService {
    List<ScheduleAction> getSchedule(long plantId) throws Exception;
    List<String> getActions() throws Exception;

    ScheduleAction addScheduleAction(ScheduleAction scheduleAction) throws Exception;
    void removeScheduleAction(ScheduleAction scheduleAction) throws Exception;
}
