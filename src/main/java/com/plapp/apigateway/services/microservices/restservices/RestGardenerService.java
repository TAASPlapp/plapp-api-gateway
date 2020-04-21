package com.plapp.apigateway.services.microservices.restservices;

import com.plapp.apigateway.services.microservices.GardenerService;
import com.plapp.entities.schedules.Diagnosis;
import com.plapp.entities.schedules.ScheduleAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
    public List<ScheduleAction> getSchedules(long plantId) {
        return restTemplate.exchange(
                baseAddress + "/gardener/" + plantId + "schedule/getAll",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ScheduleAction>>() {
                }
        ).getBody();
    }

    @Override
    public List<String> getActions() {
        return restTemplate.exchange(
                baseAddress + "/gardener/actions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {
                }
        ).getBody();
    }

    @Override
    public Diagnosis getDiagnosis(String plantImageURL, String plantId) {
        return restTemplate.getForObject(baseAddress + "/gardener/" + plantId + "/diagnose", Diagnosis.class);
    }

    @Override
    public ScheduleAction addScheduleAction(ScheduleAction scheduleAction) {
        return restTemplate.getForObject(baseAddress + "/gardener/" + scheduleAction.getPlantId() + "/schedule/add", ScheduleAction.class);
    }

    @Override
    public void removeScheduleAction(ScheduleAction scheduleAction) {
        restTemplate.getForObject(baseAddress + "/gardener/" + scheduleAction.getPlantId() + "/shcedule/remove", ScheduleAction.class);
    }
}
