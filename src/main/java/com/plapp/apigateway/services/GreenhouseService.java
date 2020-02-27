package com.plapp.apigateway.services;

import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.greenhouse.StoryboardItem;
import com.plapp.entities.utils.ApiResponse;

import java.util.List;

public interface GreenhouseService {
    List<Plant> getPlants(long userId) throws Exception;
    Plant getPlant(long plantId) throws Exception;
    ApiResponse addPlant(Plant plant) throws Exception;
    ApiResponse removePlant(Plant plant) throws Exception;

    List<Storyboard> getStoryboards() throws Exception;
    Storyboard getStoryboard(long plantId) throws Exception;
    ApiResponse createStoryboard(Storyboard storyboard) throws Exception;
    ApiResponse removeStoryboard(Storyboard storyboard) throws Exception;
    ApiResponse updateStoryboard(Storyboard storyboard) throws Exception;

    ApiResponse addStoryboardItem(StoryboardItem storyboardItem) throws Exception;
    ApiResponse removeStoryboardItem(StoryboardItem storyboardItem) throws Exception;
}
