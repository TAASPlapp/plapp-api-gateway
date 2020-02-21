package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.GreenhouseService;
import com.plapp.entities.greenhouse.Plant;
import com.plapp.entities.greenhouse.Storyboard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lists.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private GreenhouseService greenhouseService;


    @CrossOrigin
    @GetMapping("/plants")
    public List<Plant> getPlants(@RequestParam long userId) throws Exception {
       return greenhouseService.getPlants(userId);
    }

    @CrossOrigin
    @GetMapping("/plant")
    public Plant getPlant(@RequestParam long plantId) throws Exception {
        return greenhouseService.getPlant(plantId);
    }

    @CrossOrigin
    @GetMapping("/storyboards")
    public List<Storyboard> getStoryboards() throws Exception {
        return greenhouseService.getStoryboards();
    }

    @CrossOrigin
    @GetMapping("/storyboard")
    public Storyboard getStoryboard(@RequestParam long plantId) throws Exception {
        return greenhouseService.getStoryboard(plantId);
    }
}

