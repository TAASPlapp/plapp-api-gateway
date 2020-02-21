package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.SocialService;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.util.List;

@RestController
@RequestMapping("api/social")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @GetMapping("/user")
    public UserDetails getUserDetails(@RequestParam(defaultValue = "-1") long userId) throws Exception {
        return socialService.getUserDetails(userId);
    }

    @PostMapping("/user/edit")
    public ApiResponse setUserDetails(@RequestBody UserDetails userDetails) throws Exception {
        return socialService.setUserDetails(userDetails);
    }

    @GetMapping("/comments")
    public List<Comment> getComments(@RequestParam MediaContentType contentType, @RequestParam long itemId) throws  Exception {
        return socialService.getComments(contentType, itemId);
    }

    @PostMapping("/comments/add")
    public ApiResponse addComment(@RequestBody Comment comment) throws Exception {
        return socialService.addComment(comment);
    }

    @PostMapping("/likes/like")
    public ApiResponse like(@RequestBody MediaContentType contentType, @RequestParam long itemId) throws Exception {
        return socialService.like(contentType, itemId);
    }

    @PostMapping("/likes/unlike")
    public ApiResponse unlike(@RequestBody MediaContentType contentType, @RequestParam long itemId) throws Exception {
        return socialService.unlike(contentType, itemId);
    }

    @GetMapping("/likes")
    public List<UserDetails> getLikes(@RequestParam MediaContentType contentType, @RequestParam long itemId) throws Exception {
        return socialService.getLikes(contentType, itemId);
    }
}
