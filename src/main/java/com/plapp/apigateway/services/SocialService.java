package com.plapp.apigateway.services;

import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.entities.utils.ApiResponse;

import java.util.List;

public interface SocialService {
    UserDetails getUserDetails(long userId) throws Exception;

    ApiResponse addUserDetails(UserDetails user) throws Exception;

    ApiResponse updateUserDetails(UserDetails userDetails) throws Exception;

    List<Comment> getComments(MediaContentType type, long itemId) throws Exception;

    ApiResponse addComment(Comment comment) throws Exception;

    ApiResponse addLike(Like like) throws Exception;

    ApiResponse removeLike(long likeId) throws Exception;

    List<UserDetails> getLikes(MediaContentType type, long itemId) throws Exception;
}
