package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.GreenhouseService;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/greenhouse")
@RequiredArgsConstructor
@CrossOrigin
public class GreenhouseController {
    private final GreenhouseService greenhouseService;

    @GetMapping("/plants")
    public ApiResponse<List<Plant>> getPlants(@RequestParam(defaultValue = "-1") long userId) throws Exception {
       return new ApiResponse<>(greenhouseService.getPlants(userId));
    }

    @GetMapping("/plant")
    public ApiResponse<Plant> getPlant(@RequestParam long plantId) throws Exception {
        return new ApiResponse<>(greenhouseService.getPlant(plantId));
    }

    @GetMapping("/storyboards")
    public ApiResponse<List<Storyboard>> getStoryboards() throws Exception {
        return new ApiResponse<>(greenhouseService.getStoryboards());
    }


    //TODO: storyboard di un utente specifico -> per mostare il profilo
    @GetMapping("/storyboards/{userId}")
    public ApiResponse<List<Storyboard>> getStoryboards(@PathVariable long userId) throws Exception {
        return new ApiResponse<>(greenhouseService.getStoryboards());
        //return new ApiResponse<>(greenhouseService.getStoryboards(userId));

    }

    @GetMapping("/storyboard")
    public ApiResponse<Storyboard> getStoryboard(@RequestParam long plantId) throws Exception {
        return new ApiResponse<>(greenhouseService.getStoryboard(plantId));
    }

    //todo: add plant
}

