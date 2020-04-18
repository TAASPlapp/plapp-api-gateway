package com.plapp.apigateway.services.microservices;

import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;

import java.util.List;

public interface SocialService {
    UserDetails getUserDetails(long userId) ;

    UserDetails addUserDetails(UserDetails user) ;

    UserDetails setUserDetails(UserDetails userDetails);

    List<Comment> getComments(MediaContentType type, long itemId) ;

    Comment addComment(Comment comment) ;

    Like addLike(Like like) ;

    void removeLike(long likeId) ;

    List<UserDetails> getLikes(MediaContentType type, long itemId) ;
}
