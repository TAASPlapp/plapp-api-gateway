package com.plapp.apigateway.services.microservices;

import com.plapp.entities.schedules.Diagnosis;
import com.plapp.entities.schedules.ScheduleAction;

import java.util.List;

public interface GardenerService {
    List<ScheduleAction> getSchedules(long plantId) ;
    List<String> getActions() ;
    Diagnosis getDiagnosis(String plantImageUrl);
    void getDiagnosisAsync(String plantImageUrl, long plantId);
    ScheduleAction addScheduleAction(ScheduleAction scheduleAction) ;
    void removeScheduleAction(ScheduleAction scheduleAction) ;
}
