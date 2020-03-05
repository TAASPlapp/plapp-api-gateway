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

    @GetMapping("/{userId}/plants")
    public ApiResponse<List<Plant>> getPlants(@PathVariable(name = "userId") long userId) throws Exception {
       return new ApiResponse<>(greenhouseService.getPlants(userId));
    }

    @GetMapping("/plant/{plantId}")
    public ApiResponse<Plant> getPlant(@PathVariable(name = "plantId") long plantId) throws Exception {
        return new ApiResponse<>(greenhouseService.getPlant(plantId));
    }

    //todo: add plant
    @PostMapping("/{userId}/plants/add")
    public ApiResponse<Plant> addPlant(@PathVariable(name = "userId") long userId,
                                       @RequestBody Plant plant) throws Exception {
        plant.setOwner(userId);
        return new ApiResponse<>(greenhouseService.addPlant(plant));
    }


    @GetMapping("/storyboards")
    public ApiResponse<List<Storyboard>> getStoryboards() throws Exception {
        return new ApiResponse<>(greenhouseService.getStoryboards());
    }


    //TODO: storyboard di un utente specifico -> per mostare il profilo
    @GetMapping("/storyboards/{userId}")
    public ApiResponse<List<Storyboard>> getStoryboards(@PathVariable long userId) throws Exception {
        return new ApiResponse<>(greenhouseService.getStoryboards());
        //return greenhouseService.getStoryboards(userId)
    }

    @GetMapping("/storyboard")
    public ApiResponse<Storyboard> getStoryboard(@RequestParam long plantId) throws Exception {
        return new ApiResponse<>(greenhouseService.getStoryboard(plantId));
    }
}

