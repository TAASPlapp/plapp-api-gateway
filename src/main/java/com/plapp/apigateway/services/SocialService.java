package com.plapp.apigateway.services;

import com.plapp.apigateway.controllers.ApiResponse;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;

import java.util.List;

public interface SocialService {
    UserDetails getUserDetails(long userId) throws Exception;
    ApiResponse setUserDetails(UserDetails userDetails) throws Exception;

    List<Comment> getComments(MediaContentType type, long itemId) throws Exception;
    ApiResponse addComment(Comment comment) throws Exception;

    ApiResponse like(MediaContentType type, long itemId) throws Exception;
    ApiResponse unlike(MediaContentType type, long itemId) throws Exception;
    List<UserDetails> getLikes(MediaContentType type, long itemId) throws Exception;
}
