package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.SocialService;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/social")
@RequiredArgsConstructor
public class SocialController {
    private final SocialService socialService;

    @CrossOrigin
    @GetMapping("/user")
    public ApiResponse<UserDetails> getUserDetails(@RequestParam(defaultValue = "-1") long userId) throws Exception {
        return new ApiResponse<>(socialService.getUserDetails(userId));
    }

    @CrossOrigin
    @PostMapping("/user/edit")
    public ApiResponse<UserDetails> setUserDetails(@RequestBody UserDetails userDetails) throws Exception {
        return new ApiResponse<>(socialService.setUserDetails(userDetails));
    }

    @CrossOrigin
    @GetMapping("/comments")
    public ApiResponse<List<Comment>> getComments(@RequestParam MediaContentType contentType, @RequestParam long itemId) throws  Exception {
        return new ApiResponse<>(socialService.getComments(contentType, itemId));
    }

    @CrossOrigin
    @PostMapping("/comments/add")
    public ApiResponse<Comment> addComment(@RequestBody Comment comment) throws Exception {
        return new ApiResponse<>(socialService.addComment(comment));
    }

    @CrossOrigin
    @PostMapping("/likes/like")
    public ApiResponse<Like> like(@RequestBody MediaContentType contentType, @RequestParam long itemId) throws Exception {
        return new ApiResponse<>(socialService.addLike(new Like()));
    }

    @CrossOrigin
    @PostMapping("/likes/unlike")
    public ApiResponse<?> unlike(@RequestBody long likeId) throws Exception {
        Like like = new Like();
        like.setId(likeId);
        socialService.unlike(like);
        return new ApiResponse();
    }

    @CrossOrigin
    @GetMapping("/likes")
    public ApiResponse<List<UserDetails>> getLikes(@RequestParam MediaContentType contentType, @RequestParam long itemId) throws Exception {
        return new ApiResponse<>(socialService.getLikes(contentType, itemId));
    }
}
