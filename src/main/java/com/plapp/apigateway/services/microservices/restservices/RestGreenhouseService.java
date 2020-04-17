package com.plapp.apigateway.services.microservices.restservices;

import com.plapp.apigateway.services.microservices.GreenhouseService;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.greenhouse.StoryboardItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestGreenhouseService implements GreenhouseService {
    @Value("${services.greenhouse.serviceAddress}")
    private String baseAddress;

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public List<Plant> getPlants(long userId) throws Exception {
        return restTemplate.exchange(
                baseAddress + "/greenhouse/" + userId + "/plants",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Plant>>() {
                }
        ).getBody();
    }

    @Override
    public Plant addPlant(Plant plant) throws Exception {
        return restTemplate.postForObject(baseAddress + "/greenhouse/" + plant.getOwner() + "/plants/add", plant, Plant.class);
    }

    @Override
    public Plant getPlant(long plantId) throws Exception {
        return restTemplate.getForObject(baseAddress + "/greenhouse/plant/" + plantId, Plant.class);
    }

    @Override
    public void removePlant(Plant plant) throws Exception {
        restTemplate.getForObject(baseAddress + "/greenhouse/plant/" + plant.getId() + "/remove", Plant.class);
    }


    @Override
    public List<Storyboard> getStoryboards() throws Exception {
        return restTemplate.exchange(
                baseAddress + "/greenhouse/storyboards",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Storyboard>>() {
                }
        ).getBody();
    }

    @Override
    public Storyboard getStoryboard(long plantId) throws Exception {
        return restTemplate.getForObject(baseAddress + "/greenhouse/plant/" + plantId + "/storyboard", Storyboard.class);
    }

    @Override
    public Storyboard createStoryboard(Storyboard storyboard) throws Exception {
        return restTemplate.postForObject(baseAddress + "/greenhouse/plant" + storyboard.getPlant().getId() +
                "/storyboard/create", storyboard, Storyboard.class);

    }

    @Override
    public Storyboard updateStoryboard(Storyboard storyboard) throws Exception {
        return restTemplate.postForObject(baseAddress + "/greenhouse/storyboard/" + storyboard.getId() +
                "/update", storyboard, Storyboard.class);
    }

    @Override
    public void removeStoryboard(Storyboard storyboard) throws Exception {
        restTemplate.getForObject(baseAddress + "/greenhouse/storyboard/" + storyboard.getId() + "/remove", Storyboard.class);
    }

    @Override
    public StoryboardItem addStoryboardItem(StoryboardItem storyboardItem) throws Exception {
        return restTemplate.postForObject(baseAddress + "/greenhouse/storyboard/" + storyboardItem.getId() +
                "/item/add", storyboardItem, StoryboardItem.class);
    }

    @Override
    public void removeStoryboardItem(StoryboardItem storyboardItem) throws Exception {
        restTemplate.getForObject(baseAddress + "/greenhouse/storyboard/item/" + storyboardItem.getId() + "/remove", StoryboardItem.class);
    }
}