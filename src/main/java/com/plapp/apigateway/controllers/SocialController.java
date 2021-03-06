package com.plapp.apigateway.controllers;

import com.plapp.apigateway.services.config.SessionRequestContext;
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
@CrossOrigin
public class SocialController {
    private final SocialService socialService;

    @GetMapping("/user")
    public ApiResponse<UserDetails> getUserDetails(@RequestParam(defaultValue = "-1") long userId) {
        if (userId == -1L)
            userId = SessionRequestContext.getCurrentUserId();
        return new ApiResponse<>(socialService.getUserDetails(userId));
    }

    @PostMapping("/user/edit")
    public ApiResponse<UserDetails> setUserDetails(@RequestBody UserDetails userDetails) {
        return new ApiResponse<>(socialService.setUserDetails(userDetails));

    }

    @CrossOrigin
    @GetMapping("/comments")
    public ApiResponse<List<Comment>> getComments(@RequestParam MediaContentType contentType,
                                     @RequestParam long itemId) {
        return new ApiResponse<>(socialService.getComments(contentType, itemId));
    }

    @PostMapping("/comments/add")
    public ApiResponse<Comment> addComment(@RequestBody Comment comment) {
        return new ApiResponse<>(socialService.addComment(comment));
    }

    @PostMapping("/likes/like")
    public ApiResponse<Like> like(@RequestBody Like like) {
        return new ApiResponse<>(socialService.addLike(like));
    }

    @PostMapping("/likes/unlike")
    public void unlike(@RequestBody long likeId) {
        socialService.removeLike(likeId);
    }

    @GetMapping("/likes")
    public ApiResponse<List<UserDetails>> getLikes(@RequestParam MediaContentType contentType, @RequestParam long itemId) {
        return new ApiResponse<>(socialService.getLikes(contentType, itemId));
    }
}
