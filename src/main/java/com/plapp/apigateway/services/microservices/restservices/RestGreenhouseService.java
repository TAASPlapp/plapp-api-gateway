package com.plapp.apigateway.services.microservices.restservices;

import com.plapp.apigateway.services.SessionTokenService;
import com.plapp.apigateway.services.config.SessionRequestContext;
import com.plapp.apigateway.services.microservices.Authorities;
import com.plapp.apigateway.services.microservices.AuthorizationService;
import com.plapp.apigateway.services.microservices.GreenhouseService;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.greenhouse.StoryboardItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestGreenhouseService implements GreenhouseService {
    @Value("${services.greenhouse.serviceAddress}")
    private String baseAddress;

    @Autowired
    public RestTemplate restTemplate;

    private final AuthorizationService authorizationService;
    private final SessionTokenService sessionTokenService;

    @Override
    public List<Plant> getPlants(long userId) {
        return restTemplate.exchange(
                baseAddress + "/greenhouse/" + userId + "/plants",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Plant>>() {
                }
        ).getBody();
    }

    @Override
    public Plant addPlant(Plant plant) {
        return restTemplate.postForObject(
                baseAddress + String.format("/greenhouse/%d/plants/add", + plant.getOwner()),
                plant,
                Plant.class
        );
    }

    @Override
    public Plant getPlant(long plantId) {
        return restTemplate.getForObject(baseAddress + "/greenhouse/plant/" + plantId, Plant.class);
    }

    @Override
    public void removePlant(Plant plant) {
        restTemplate.getForObject(baseAddress + "/greenhouse/plant/" + plant.getId() + "/remove", Plant.class);
    }


    @Override
    public List<Storyboard> getStoryboards() {
        return restTemplate.exchange(
                baseAddress + "/greenhouse/storyboards",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Storyboard>>() {
                }
        ).getBody();
    }

    @Override
    public List<Storyboard> getStoryboards(long userId) {
        return restTemplate.exchange(
                baseAddress + String.format("/greenhouse/%d/storyboards", userId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Storyboard>>() {
                }
        ).getBody();
    }

    @Override
    public Storyboard getStoryboard(long plantId) {
        return restTemplate.getForObject(baseAddress + "/greenhouse/plant/" + plantId + "/storyboard", Storyboard.class);
    }

    @Override
    public Storyboard createStoryboard(Storyboard storyboard) {
        return restTemplate.postForObject(
                baseAddress + String.format("/greenhouse/plant/%d/storyboard/create" , storyboard.getPlant().getId()),
                storyboard,
                Storyboard.class);
    }

    @Override
    public Storyboard updateStoryboard(Storyboard storyboard) {
        return restTemplate.postForObject(baseAddress + "/greenhouse/storyboard/" + storyboard.getId() +
                "/update", storyboard, Storyboard.class);
    }

    @Override
    public void removeStoryboard(Storyboard storyboard) {
        restTemplate.getForObject(baseAddress + "/greenhouse/storyboard/" + storyboard.getId() + "/remove", Storyboard.class);
    }

    @Override
    public StoryboardItem addStoryboardItem(StoryboardItem storyboardItem) {
        return restTemplate.postForObject(
                baseAddress + String.format("/greenhouse/storyboard/%d/item/add", storyboardItem.getStoryboardId()),
                storyboardItem,
                StoryboardItem.class
        );
    }

    @Override
    public void removeStoryboardItem(StoryboardItem storyboardItem) {
        restTemplate.getForObject(baseAddress + "/greenhouse/storyboard/item/" + storyboardItem.getId() + "/remove", StoryboardItem.class);
    }
}
