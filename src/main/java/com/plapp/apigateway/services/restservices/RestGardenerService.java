package com.plapp.apigateway.services.restservices;

import com.plapp.apigateway.services.GardenerService;
import com.plapp.entities.schedules.ScheduleAction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestGardenerService implements GardenerService {
    @Value("${services.gardener.serviceAddress}")
    private String baseAddress;

    @Override
    public List<ScheduleAction> getSchedule(long plantId) throws Exception {
        return null;
    }

    @Override
    public List<String> getActions() throws Exception {
        return null;
    }

    @Override
    public ScheduleAction addScheduleAction(ScheduleAction scheduleAction) throws Exception {
        //gardener/{plantId}/schedule/add
        RestTemplate restTemplate = new RestTemplate();
        //todo: http put in gardener -> retrun void!!!
        //return restTemplate.put(baseAddress + "/gardener/" + scheduleAction.getPlantId() + "/schedule/add", scheduleAction);
        return null;
    }

    @Override
    public void removeScheduleAction(ScheduleAction scheduleAction) throws Exception {

    }
}
