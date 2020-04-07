package com.plapp.apigateway.services.restservices;

import com.plapp.apigateway.services.GreenhouseService;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.greenhouse.StoryboardItem;
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

    @Override
    public List<Plant> getPlants(long userId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                baseAddress + userId + "/plants",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Plant>>() {
                }
        ).getBody();
    }

    @Override
    public Plant getPlant(long plantId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(baseAddress + "/plant/" + plantId, Plant.class);
    }

    @Override
    public Plant addPlant(Plant plant) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseAddress + '/' + plant.getOwner() + "/plants/add", plant, Plant.class);
    }

    @Override
    public void removePlant(Plant plant) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(baseAddress + "/plant/" + plant.getId(), Plant.class);
    }

    @Override
    public List<Storyboard> getStoryboards() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                baseAddress + "/storyboards",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Storyboard>>() {}
        ).getBody();
    }

    @Override
    public Storyboard getStoryboard(long plantId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(baseAddress + "/plant/" + plantId + "/storyboard", Storyboard.class);
    }

    @Override
    public Storyboard createStoryboard(Storyboard storyboard) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseAddress + "/plant" + storyboard.getPlant().getId() +
                "/storyboard/create", storyboard, Storyboard.class);

    }

    @Override
    public Storyboard updateStoryboard(Storyboard storyboard) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseAddress + "/storyboard/" + storyboard.getId() +
                "/update", storyboard, Storyboard.class);
    }

    @Override
    public void removeStoryboard(Storyboard storyboard) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(baseAddress + "/storyboard/" + storyboard.getId() + "/remove", Storyboard.class);
    }

    @Override
    public StoryboardItem addStoryboardItem(StoryboardItem storyboardItem) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseAddress + "/storyboard/" + storyboardItem.getId() +
                "/item/add", storyboardItem, StoryboardItem.class);
    }

    @Override
    public void removeStoryboardItem(StoryboardItem storyboardItem) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(baseAddress + "/storyboard/item/" + storyboardItem.getId() + "/remove", StoryboardItem.class);
    }
}
