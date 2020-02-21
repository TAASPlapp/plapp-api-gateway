package com.plapp.apigateway.services;

import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;

import java.util.List;

public interface GreenhouseService {
    List<Plant> getPlants(long userId) throws Exception;
    Plant getPlant(long plantId) throws Exception;

    List<Storyboard> getStoryboards() throws Exception;
    Storyboard getStoryboard(long plantId) throws Exception;
}
