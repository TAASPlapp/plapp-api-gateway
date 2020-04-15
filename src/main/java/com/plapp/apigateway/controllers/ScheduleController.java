package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.microservices.GardenerService;
import com.plapp.entities.schedules.ScheduleAction;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final GardenerService gardenerService;

    @CrossOrigin
    @GetMapping("/")
    public ApiResponse<List<ScheduleAction>> getSchedule(@RequestParam(value="plantId", defaultValue="-1") long plantId) throws Exception {
        return new ApiResponse<>(gardenerService.getSchedule(plantId));
    }

    @CrossOrigin
    @GetMapping("/actions")
    public ApiResponse<List<String>> getScheduleActions() throws Exception {
       return new ApiResponse<>(gardenerService.getActions());
    }

    @CrossOrigin
    @PostMapping("/add")
    public ApiResponse<ScheduleAction> addScheduleAction(@RequestBody ScheduleAction action) throws Exception {
        gardenerService.addScheduleAction(action);
        return new ApiResponse<>();
    }

    @CrossOrigin
    @PostMapping("/remove")
    public ApiResponse<?> removeScheduleAction(@RequestBody ScheduleAction action) throws Exception {
        gardenerService.removeScheduleAction(action);
        return new ApiResponse();
    }
}
