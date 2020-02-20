package com.plapp.apigateway.controllers;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;

@RestController
@RequestMapping("api/social")
public class SocialController {


    @CrossOrigin
    @GetMapping("/storyboards")
    public String getStoryboards() throws Exception {
        JSONParser parser = new JSONParser();

        File jsonFile = new ClassPathResource("mock-response/mock-storyboard.json").getFile();
        JSONArray storyboards = (JSONArray)parser.parse(new FileReader(jsonFile));

        return storyboards.toJSONString();
    }
}
