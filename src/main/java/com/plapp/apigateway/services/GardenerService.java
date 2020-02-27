package com.plapp.apigateway.services;

import com.plapp.entities.schedules.ScheduleAction;
import com.plapp.entities.utils.ApiResponse;

import java.util.List;

public interface GardenerService {
    List<ScheduleAction> getSchedule(long plantId) throws Exception;
    List<String> getActions() throws Exception;

    ApiResponse addScheduleAction(ScheduleAction scheduleAction) throws Exception;
    ApiResponse removeScheduleAction(ScheduleAction scheduleAction) throws Exception;
}
