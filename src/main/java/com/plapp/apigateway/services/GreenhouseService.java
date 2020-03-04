package com.plapp.apigateway.services;

import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.greenhouse.StoryboardItem;
import com.plapp.entities.utils.ApiResponse;

import java.util.List;

public interface GreenhouseService {
    ApiResponse<List<Plant>> getPlants(long userId) throws Exception;
    ApiResponse<Plant> getPlant(long plantId) throws Exception;
    ApiResponse<Plant> addPlant(Plant plant) throws Exception;
    ApiResponse removePlant(Plant plant) throws Exception;

    ApiResponse<List<Storyboard>> getStoryboards() throws Exception;
    ApiResponse<Storyboard> getStoryboard(long plantId) throws Exception;
    ApiResponse<Storyboard> createStoryboard(Storyboard storyboard) throws Exception;
    ApiResponse removeStoryboard(Storyboard storyboard) throws Exception;
    ApiResponse<Storyboard> updateStoryboard(Storyboard storyboard) throws Exception;

    ApiResponse<StoryboardItem> addStoryboardItem(StoryboardItem storyboardItem) throws Exception;
    ApiResponse<StoryboardItem> removeStoryboardItem(StoryboardItem storyboardItem) throws Exception;
}
