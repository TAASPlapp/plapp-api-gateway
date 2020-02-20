package com.plapp.apigateway.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lists.utils.Lists;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/greenhouse")
public class GreenhouseController {

    //TODO: capire perchè senza cross origin ora non mi funziona più...
    @CrossOrigin
    @GetMapping("/plants")
    public String getPlants() throws Exception {
        File jsonFile = new ClassPathResource("mock-response/mock-plants.json").getFile();
        return new String(Files.readAllBytes(jsonFile.toPath()), StandardCharsets.UTF_8);
    }

    @CrossOrigin
    @GetMapping("/plant")
    public String getPlant(@RequestParam long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-plants.json").getFile();
        JSONArray plants = (JSONArray)parser.parse(new FileReader(jsonFile));
        List<JSONObject> plant = Lists.<JSONObject>filter(
                plants.iterator(),
                p -> ((Long)p.get("id")) == plantId
        );

        return plant.get(0).toJSONString();
    }

    @CrossOrigin
    @GetMapping("/storyboard")
    public String getStoryboard(@RequestParam long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-storyboard.json").getFile();
        JSONArray storyboards = (JSONArray)parser.parse(new FileReader(jsonFile));
        List<JSONObject> storyboard = Lists.<JSONObject>filter(
                storyboards.iterator(),
                p -> ((Long)p.get("plantId")) == plantId
        );

        return storyboard.get(0).toJSONString();
    }

    @CrossOrigin
    @GetMapping("/schedules")
    public String getSchedules(@RequestParam long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-schedule.json").getFile();
        JSONArray schedules = (JSONArray)parser.parse(new FileReader(jsonFile));
        List<JSONObject> schedule = Lists.<JSONObject>filter(
                schedules.iterator(),
                p -> ((Long)p.get("plantId")) == plantId
        );

        return schedule.toString();
    }
}

