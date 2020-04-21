package com.plapp.apigateway.services.microservices.restservices;

import com.plapp.apigateway.services.microservices.Authorities;
import com.plapp.apigateway.services.microservices.AuthorizationService;
import com.plapp.apigateway.services.microservices.GardenerService;
import com.plapp.entities.schedules.Diagnosis;
import com.plapp.entities.schedules.ScheduleAction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestGardenerService implements GardenerService {
    @Value("${services.gardener.serviceAddress}")
    private String baseAddress;

    @Autowired
    public RestTemplate restTemplate;

    private final AuthorizationService authorizationService;

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
    public Diagnosis getDiagnosis(String plantImageURL) {
        System.out.println("plantImageURL: " + plantImageURL);

        Map<String, String> params = new HashMap<>();
        params.put("plantImageURL", plantImageURL);

        return restTemplate.postForObject(
                baseAddress + "/gardener/diagnose",
                params,
                Diagnosis.class
        );
    }

    @Override
    public void getDiagnosisAsync(String plantImageURL, long plantId) {
        restTemplate.postForEntity(
            baseAddress + String.format("/gardener/%d/diagnose-async", plantId),
            null,
            Void.class,
            plantImageURL);
    }

    @Override
    public ScheduleAction addScheduleAction(ScheduleAction scheduleAction) {
        ScheduleAction addedScheduleAction = restTemplate.getForObject(baseAddress + "/gardener/" + scheduleAction.getPlantId() + "/schedule/add", ScheduleAction.class);
        authorizationService.updateAuthorization(Authorities.GARDENER_SCHEDULE, addedScheduleAction.getScheduleActionId());
        return addedScheduleAction;
    }

    @Override
    public void removeScheduleAction(ScheduleAction scheduleAction) {
        restTemplate.getForObject(baseAddress + "/gardener/" + scheduleAction.getPlantId() + "/shcedule/remove", ScheduleAction.class);
    }
}
