package com.plapp.apigateway.services.microservices.restservices;

import com.plapp.apigateway.services.microservices.GardenerService;
import com.plapp.entities.schedules.ScheduleAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestGardenerService implements GardenerService {
    @Value("${services.gardener.serviceAddress}")
    private String baseAddress;

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public List<ScheduleAction> getSchedule(long plantId) {
        return null;
    }

    @Override
    public List<String> getActions() {
        return null;
    }

    @Override
    public ScheduleAction addScheduleAction(ScheduleAction scheduleAction) {
        //gardener/{plantId}/schedule/add
        //todo: http put in gardener -> retrun void!!!
        //return restTemplate.put(baseAddress + "/gardener/" + scheduleAction.getPlantId() + "/schedule/add", scheduleAction);
        return null;
    }

    @Override
    public void removeScheduleAction(ScheduleAction scheduleAction) {

    }
}
