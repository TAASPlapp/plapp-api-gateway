package com.plapp.apigateway.services;

import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.greenhouse.StoryboardItem;
import com.plapp.entities.utils.ApiResponse;

import java.util.List;

public interface GreenhouseService {
    List<Plant> getPlants(long userId) throws Exception;
    Plant getPlant(long plantId) throws Exception;
    Plant addPlant(Plant plant) throws Exception;
    void removePlant(Plant plant) throws Exception;

    List<Storyboard> getStoryboards() throws Exception;
    Storyboard getStoryboard(long plantId) throws Exception;
    Storyboard createStoryboard(Storyboard storyboard) throws Exception;
    Storyboard updateStoryboard(Storyboard storyboard) throws Exception;
    void removeStoryboard(Storyboard storyboard) throws Exception;

    StoryboardItem addStoryboardItem(StoryboardItem storyboardItem) throws Exception;
    void removeStoryboardItem(StoryboardItem storyboardItem) throws Exception;
}
