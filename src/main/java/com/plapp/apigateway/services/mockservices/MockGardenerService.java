package com.plapp.apigateway.services.mockservices;

import com.plapp.apigateway.controllers.ApiResponse;
import com.plapp.apigateway.services.GardenerService;
import com.plapp.entities.schedules.ScheduleAction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lists.utils.Lists;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MockGardenerService implements GardenerService {

    private ScheduleAction jsonToScheduleActon(JSONObject json) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = formatter.parse((String)json.get("date"));
        } catch (Exception e) {

        }

        ScheduleAction action = new ScheduleAction(
                (Long)json.get("userId"),
                (Long)json.get("plantId"),
                date,
                (String)json.get("action"),
                ((Long)json.get("periodicity")).intValue(),
                ((String)json.get("additionalInfo"))

        );

        return action;
    }

    @Override
    public List<ScheduleAction> getSchedule(long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-schedule.json").getFile();
        JSONArray schedules = (JSONArray)parser.parse(new FileReader(jsonFile));

        List<ScheduleAction> scheduleActions = Lists.map(
                schedules.iterator(),
                this::jsonToScheduleActon
        );

        List<ScheduleAction> schedule = Lists.filter(
                scheduleActions,
                p -> p.getPlantId() == plantId
        );

        return schedule;
    }

    @Override
    public List<String> getActions() throws Exception {
        return Arrays.asList("Watering", "Manure", "Harvest", "Pruning", "Treating");
    }

    @Override
    public ApiResponse addScheduleAction(ScheduleAction scheduleAction) throws Exception {
        return new ApiResponse();
    }

    @Override
    public ApiResponse removeScheduleAction(ScheduleAction scheduleAction) throws Exception {
        return new ApiResponse();
    }
}
