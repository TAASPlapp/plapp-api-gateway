package com.plapp.apigateway.services.microservices;

import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;

import java.util.List;

public interface SocialService {
    UserDetails getUserDetails(long userId) throws Exception;

    UserDetails addUserDetails(UserDetails user) throws Exception;

    UserDetails setUserDetails(UserDetails userDetails);

    List<Comment> getComments(MediaContentType type, long itemId) throws Exception;

    Comment addComment(Comment comment) throws Exception;

    Like addLike(Like like) throws Exception;

    void removeLike(long likeId) throws Exception;

    List<UserDetails> getLikes(MediaContentType type, long itemId) throws Exception;
}
