package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.microservices.SocialService;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.entities.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
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
    public List<Comment> getComments(@RequestParam MediaContentType contentType,
                                     @RequestParam long itemId) throws Exception {
        return socialService.getComments(contentType, itemId);
    }

    @CrossOrigin
    @PostMapping("/comments/add")
    public ApiResponse<Comment> addComment(@RequestBody Comment comment) throws Exception {
        return new ApiResponse<>(socialService.addComment(comment));
    }

    @CrossOrigin
    @PostMapping("/likes/like")
    public ApiResponse<Like> like(@RequestBody Like like) throws Exception {
        return new ApiResponse<>(socialService.addLike(like));
    }

    @CrossOrigin
    @PostMapping("/likes/unlike")
    public void unlike(@RequestBody long likeId) throws Exception {
        socialService.removeLike(likeId);
    }

    @CrossOrigin
    @GetMapping("/likes")
    public ApiResponse<List<UserDetails>> getLikes(@RequestParam MediaContentType contentType, @RequestParam long itemId) throws Exception {
        return new ApiResponse<>(socialService.getLikes(contentType, itemId));
    }
}
