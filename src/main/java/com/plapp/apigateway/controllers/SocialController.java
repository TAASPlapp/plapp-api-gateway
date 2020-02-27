package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.SocialService;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/social")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @CrossOrigin
    @GetMapping("/user")
    public UserDetails getUserDetails(@RequestParam(defaultValue = "-1") long userId) throws Exception {
        return socialService.getUserDetails(userId);
    }

    @CrossOrigin
    @PostMapping("/user/edit")
    public ApiResponse setUserDetails(@RequestBody UserDetails userDetails) throws Exception {
        return socialService.setUserDetails(userDetails);
    }

    @CrossOrigin
    @GetMapping("/comments")
    public List<Comment> getComments(@RequestParam MediaContentType contentType, @RequestParam long itemId) throws  Exception {
        return socialService.getComments(contentType, itemId);
    }

    @CrossOrigin
    @PostMapping("/comments/add")
    public ApiResponse addComment(@RequestBody Comment comment) throws Exception {
        return socialService.addComment(comment);
    }

    @CrossOrigin
    @PostMapping("/likes/like")
    public ApiResponse like(@RequestBody MediaContentType contentType, @RequestParam long itemId) throws Exception {
        return socialService.addLike(new Like());
    }

    @CrossOrigin
    @PostMapping("/likes/unlike")
    public ApiResponse unlike(@RequestBody long likeId) throws Exception {
        return socialService.unlike(likeId);
    }

    @CrossOrigin
    @GetMapping("/likes")
    public List<UserDetails> getLikes(@RequestParam MediaContentType contentType, @RequestParam long itemId) throws Exception {
        return socialService.getLikes(contentType, itemId);
    }
}
