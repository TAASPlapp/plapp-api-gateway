package com.plapp.apigateway.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lists.utils.Lists;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/greenhouse")
public class GreenhouseController {

    @GetMapping("/plants")
    public String getPlants(long userId) throws Exception {
        File jsonFile = new ClassPathResource("mock-response/mock-plants.json").getFile();
        return new String(Files.readAllBytes(jsonFile.toPath()), StandardCharsets.UTF_8);
    }

    @GetMapping("/plant")
    public String getPlant(long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-plants.json").getFile();
        JSONArray plants = (JSONArray)parser.parse(new FileReader(jsonFile));
        List<JSONObject> plant = Lists.<JSONObject>filter(
                plants.iterator(),
                p -> ((Long)p.get("id")) == plantId
        );

        return plant.get(0).toJSONString();
    }

    @GetMapping("/storyboard")
    public String getStoryboard(long plantId) throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-storyboard.json").getFile();
        JSONArray storyboards = (JSONArray)parser.parse(new FileReader(jsonFile));
        List<JSONObject> storyboard = Lists.<JSONObject>filter(
                storyboards.iterator(),
                p -> ((Long)p.get("plantId")) == plantId
        );

        return storyboard.get(0).toJSONString();
    }
}
