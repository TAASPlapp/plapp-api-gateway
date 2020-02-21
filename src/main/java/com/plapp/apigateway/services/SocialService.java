package com.plapp.apigateway.services;

import com.plapp.apigateway.controllers.ApiResponse;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;

import java.util.List;

public interface SocialService {
    UserDetails getUserDetails(long userId);
    ApiResponse setUserDetails(UserDetails userDetails);

    List<Comment> getComments(MediaContentType type, long itemId);
    ApiResponse addComment(Comment comment);

    ApiResponse like(MediaContentType type, long itemId);
    ApiResponse unlike(MediaContentType type, long itemId);
    List<UserDetails> getLikes(MediaContentType type, long itemId);
}
