package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.config.SessionRequestContext;
import com.plapp.apigateway.services.microservices.GardenerService;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.schedules.ScheduleAction;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/gardener")
@RequiredArgsConstructor
@CrossOrigin
public class GardenerController {
    private final GardenerService gardenerService;

    @CrossOrigin
    @GetMapping(value = "gardener/{plantId}/diagnose")
    public ApiResponse<String> getPlantDiagnosis(String plantImageURL, String plantId){
        return new ApiResponse<>(gardenerService.getDiagnosis(plantImageURL,plantId));
    }


}