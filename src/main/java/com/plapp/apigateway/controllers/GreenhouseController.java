package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.microservices.GreenhouseService;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/greenhouse")
@RequiredArgsConstructor
@CrossOrigin
public class GreenhouseController {
    private final GreenhouseService greenhouseService;

    @GetMapping(value= {"/plants", "/{userId}/plants"})
    public ApiResponse<List<Plant>> getPlants(@PathVariable(name="userId", required = false) Optional<Long> optId) {
        long userId = optId.orElse(-1L);
        return new ApiResponse<>(greenhouseService.getPlants(userId));
    }

    @GetMapping("/plant/{plantId}")
    public ApiResponse<Plant> getPlant(@PathVariable(name = "plantId") long plantId) {
        return new ApiResponse<>(greenhouseService.getPlant(plantId));
    }

    //todo: add plant
    @PostMapping("/plants/add")
    public ApiResponse<Plant> addPlant(@RequestBody Plant plant) {
        return new ApiResponse<>(greenhouseService.addPlant(plant));
    }


    @GetMapping("/storyboards/feed")
    public ApiResponse<List<Storyboard>> getStoryboards() {
        return new ApiResponse<>(greenhouseService.getStoryboards());
    }


    //TODO: storyboard di un utente specifico -> per mostare il profilo
    @GetMapping(value = {"/storyboards", "/storyboards/{userId}"})
    public ApiResponse<List<Storyboard>> getStoryboards(@PathVariable(name = "userId") Optional<Long> optId) {
        Long userId = optId.orElse(-1L);
        return new ApiResponse<>(greenhouseService.getStoryboards());
        //return greenhouseService.getStoryboards(userId)
    }

    @GetMapping("/plant/{plantId}/storyboard")
    public ApiResponse<Storyboard> getStoryboard(@PathVariable long plantId) {
        return new ApiResponse<>(greenhouseService.getStoryboard(plantId));
    }
}

