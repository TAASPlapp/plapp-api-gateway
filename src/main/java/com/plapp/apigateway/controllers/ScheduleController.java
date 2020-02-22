package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.GardenerService;
import com.plapp.entities.schedules.ScheduleAction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lists.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private GardenerService gardenerService;

    @GetMapping("/")
    public List<ScheduleAction> getSchedule(@RequestParam(value="plantId", defaultValue="-1") long plantId) throws Exception {
        return gardenerService.getSchedule(plantId);
    }

    @GetMapping("/actions")
    public List<String> getScheduleActions() throws Exception {
       return gardenerService.getActions();
    }

    @PostMapping("/add")
    public ApiResponse addScheduleAction(@RequestBody ScheduleAction action) throws Exception {
        return gardenerService.addScheduleAction(action);
    }

    @PostMapping("/remove")
    public ApiResponse removeScheduleAction(@RequestBody ScheduleAction action) throws Exception {
        return gardenerService.removeScheduleAction(action);
    }
}
