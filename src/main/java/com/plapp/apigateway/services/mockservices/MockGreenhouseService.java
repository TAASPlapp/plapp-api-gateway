package com.plapp.apigateway.services.mockservices;

import com.plapp.apigateway.services.GreenhouseService;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import com.plapp.entities.greenhouse.StoryboardItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lists.utils.Lists;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MockGreenhouseService implements GreenhouseService {

    private Plant jsonToPlant(JSONObject jsonPlant) {
        Plant plant = new Plant((Long)jsonPlant.get("id"));
        plant.setDescription((String)jsonPlant.get("description"));
        plant.setOwner((Long)jsonPlant.get("owner"));
        plant.setName((String)jsonPlant.get("name"));
        plant.setType((String)jsonPlant.get("type"));
        plant.setImage((String)jsonPlant.get("image"));
        return plant;
    }

    private Storyboard jsonToStoryboard(JSONObject jsonStoryboard) {
        Storyboard storyboard = new Storyboard((Long)jsonStoryboard.get("id"));
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            storyboard.setLastModified(formatter.parse((String) jsonStoryboard.get("lastModified")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        storyboard.setNumLikes(((Long)jsonStoryboard.get("numLikes")).intValue());
        storyboard.setSummary((String)jsonStoryboard.get("summary"));

        List<StoryboardItem> items = Lists.<JSONObject, StoryboardItem>map(
                ((JSONArray)jsonStoryboard.get("storyboardItems")).iterator(),
                json -> {
                    StoryboardItem item = new StoryboardItem((Long)json.get("id"));
                    item.setImage((String)json.get("image"));
                    item.setDescription((String)json.get("description"));
                    item.setTitle((String)json.get("title"));
                    item.setNumLikes(((Long)json.get("numLikes")).intValue());
                    item.setStatus(Plant.PlantHealthStatus.valueOf(((String)json.get("status")).toUpperCase()));
                    return item;
                }
        );

        storyboard.setStoryboardItems(items);

        return storyboard;
    }

    @Override
    public List<Plant> getPlants(long userId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-plants.json").getFile();
        List<JSONObject> jsonPlants = Lists.<JSONObject>filter(
                (JSONArray)parser.parse(new FileReader(jsonFile)),
                p -> ((Long)p.get("owner")) == userId
        );

        List<Plant> plants = Lists.map(
                jsonPlants,
                this::jsonToPlant
        );

        return plants;
    }

    @Override
    public Plant getPlant(long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-plants.json").getFile();
        JSONObject jsonPlant = (JSONObject)Lists.<JSONObject>filter(
                (JSONArray)parser.parse(new FileReader(jsonFile)),
                p -> ((Long)p.get("id")) == plantId
        ).get(0);

        return jsonToPlant(jsonPlant);
    }

    @Override
    public List<Storyboard> getStoryboards() throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-storyboard.json").getFile();
        JSONArray jsonStoryboards = (JSONArray)parser.parse(new FileReader(jsonFile));

        List<Storyboard> storyboards = Lists.<JSONObject, Storyboard>map(
                jsonStoryboards.iterator(),
                this::jsonToStoryboard
        );
        return storyboards;
    }

    @Override
    public Storyboard getStoryboard(long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-storyboard.json").getFile();
        JSONArray storyboards = (JSONArray) parser.parse(new FileReader(jsonFile));
        List<JSONObject> jsonStoryboards = Lists.<JSONObject>filter(
                storyboards.iterator(),
                p -> ((Long)((JSONObject)p.get("plant")).get("id")) == plantId
        );
        return jsonToStoryboard(jsonStoryboards.get(0));
    }
}
