package com.plapp.apigateway.services;

import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;

import java.util.List;

public interface GreenhouseService {
    List<Plant> getPlants(long userId);
    Plant getPlant(long plantId);
    Storyboard getStoryboard(long plantId);
}
