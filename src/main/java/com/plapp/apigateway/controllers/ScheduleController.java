package com.plapp.apigateway.controllers;

import com.plapp.entities.schedules.ScheduleAction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lists.utils.Lists;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @GetMapping("/")
    public String getSchedules(@RequestParam(value="plantId", defaultValue="-1") long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-schedule.json").getFile();
        JSONArray schedules = (JSONArray)parser.parse(new FileReader(jsonFile));
        List<JSONObject> schedule = Lists.<JSONObject>filter(
                schedules.iterator(),
                p -> ((Long)p.get("plantId")) == plantId
        );

        return schedule.toString();
    }

    @GetMapping("/actions")
    public List<String> getScheduleActions() {
        return Arrays.asList("Watering", "Manure", "Harvest", "Pruning", "Treating");
    }

    @PostMapping("/add")
    public ApiResponse addScheduleAction(@RequestParam ScheduleAction action) {
        return new ApiResponse();
    }
}
