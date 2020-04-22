package com.plapp.apigateway.controllers;

import com.plapp.apigateway.saga.greenhouse.PlantAddSagaOrchestrator;
import com.plapp.apigateway.saga.orchestration.SagaExecutionException;
import com.plapp.apigateway.services.config.SessionRequestContext;
import com.plapp.apigateway.services.microservices.GreenhouseService;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.greenhouse.StoryboardItem;
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
    private final PlantAddSagaOrchestrator plantAddSagaOrchestrator;

    @GetMapping(value= {"/plants", "/{userId}/plants"})
    public ApiResponse<List<Plant>> getPlants(@PathVariable(name="userId", required = false) Optional<Long> optId) {
        long userId = optId.orElse(-1L);
        if (userId == -1L)
            userId = SessionRequestContext.getCurrentUserId();

        return new ApiResponse<>(greenhouseService.getPlants(userId));
    }

    @GetMapping("/plant/{plantId}")
    public ApiResponse<Plant> getPlant(@PathVariable(name = "plantId") long plantId) {
        return new ApiResponse<>(greenhouseService.getPlant(plantId));
    }

    @PostMapping("/plants/add")
    public ApiResponse<Plant> addPlant(@RequestBody Plant plant) throws SagaExecutionException, Throwable {
        return new ApiResponse<>(plantAddSagaOrchestrator.addPlant(plant));
    }


    @GetMapping("/storyboards/feed")
    public ApiResponse<List<Storyboard>> getStoryboards() {
        return new ApiResponse<>(greenhouseService.getStoryboards());
    }


    @GetMapping(value = {"/storyboards", "/storyboards/{userId}"})
    public ApiResponse<List<Storyboard>> getStoryboards(@PathVariable(name = "userId") Optional<Long> optId) {
        long userId = optId.orElse(-1L);
        if (userId == -1L)
            return new ApiResponse<>(greenhouseService.getStoryboards());
        return new ApiResponse<>(greenhouseService.getStoryboards(userId));
    }

    @GetMapping("/plant/{plantId}/storyboard")
    public ApiResponse<Storyboard> getStoryboard(@PathVariable long plantId) {
        return new ApiResponse<>(greenhouseService.getStoryboard(plantId));
    }

    @GetMapping("/storyboard/{storyboardId}/item/add")
    public ApiResponse<StoryboardItem> addStoryboardItem(@PathVariable long storyboardId, @RequestBody StoryboardItem item) {
        item.setStoryboardId(storyboardId);
        return new ApiResponse<>(greenhouseService.addStoryboardItem(item));
    }
}

