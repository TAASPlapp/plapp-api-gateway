package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.microservices.GardenerService;
import com.plapp.entities.schedules.Diagnosis;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/gardener")
public class GardenerController {
    private final GardenerService gardenerService;

    @PostMapping("/diagnose")
    public ApiResponse<Diagnosis> getPlantDiagnosis(@RequestBody Map<String, String> params){
        String plantImageURL = params.get("plantImageURL");
        return new ApiResponse<>(gardenerService.getDiagnosis(plantImageURL));
    }
}
