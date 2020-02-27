package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.GardenerService;
import com.plapp.entities.schedules.ScheduleAction;
import com.plapp.entities.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schedule")
public class ScheduleController {

    @Autowired
    private GardenerService gardenerService;

    @CrossOrigin
    @GetMapping("/")
    public List<ScheduleAction> getSchedule(@RequestParam(value="plantId", defaultValue="-1") long plantId) throws Exception {
        return gardenerService.getSchedule(plantId);
    }

    @CrossOrigin
    @GetMapping("/actions")
    public List<String> getScheduleActions() throws Exception {
       return gardenerService.getActions();
    }

    @CrossOrigin
    @PostMapping("/add")
    public ApiResponse addScheduleAction(@RequestBody ScheduleAction action) throws Exception {
        return gardenerService.addScheduleAction(action);
    }

    @CrossOrigin
    @PostMapping("/remove")
    public ApiResponse removeScheduleAction(@RequestBody ScheduleAction action) throws Exception {
        return gardenerService.removeScheduleAction(action);
    }
}
