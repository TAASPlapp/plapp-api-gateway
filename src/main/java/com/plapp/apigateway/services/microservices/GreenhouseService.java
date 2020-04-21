package com.plapp.apigateway.services.microservices;

import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.greenhouse.StoryboardItem;

import java.util.List;

public interface GreenhouseService {
    List<Plant> getPlants(long userId) ;
    Plant getPlant(long plantId) ;
    Plant addPlant(Plant plant) ;
    void removePlant(Plant plant) ;

    List<Storyboard> getStoryboards();
    List<Storyboard> getStoryboards(long userId);

    Storyboard getStoryboard(long plantId) ;
    Storyboard createStoryboard(Storyboard storyboard) ;
    Storyboard updateStoryboard(Storyboard storyboard) ;
    void removeStoryboard(Storyboard storyboard) ;

    StoryboardItem addStoryboardItem(StoryboardItem storyboardItem) ;
    void removeStoryboardItem(StoryboardItem storyboardItem) ;
}
