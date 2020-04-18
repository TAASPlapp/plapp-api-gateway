package com.plapp.apigateway.services.microservices;

import com.plapp.entities.schedules.ScheduleAction;

import java.util.List;

public interface GardenerService {
    List<ScheduleAction> getSchedule(long plantId) ;
    List<String> getActions() ;

    ScheduleAction addScheduleAction(ScheduleAction scheduleAction) ;
    void removeScheduleAction(ScheduleAction scheduleAction) ;
}
