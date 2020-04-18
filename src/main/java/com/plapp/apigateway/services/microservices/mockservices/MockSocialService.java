package com.plapp.apigateway.services.microservices.mockservices;

import com.plapp.apigateway.services.microservices.SocialService;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

public class MockSocialService implements SocialService {

    private Comment jsonToComment(JSONObject json) {
        MediaContentType contentType = MediaContentType.valueOf((String)json.get("mediaContentType"));
        long itemId = (Long)json.get("itemId");

        try {
            UserDetails userDetails = getUserDetails(2);
            Comment comment = new Comment(contentType, itemId, userDetails);
            comment.setPublishedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse((String)json.get("publishedAt")));
            comment.setContent((String)json.get("content"));
            return comment;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    @Override
    public UserDetails getUserDetails(long userId)  {
        return null;
    }

    @Override
    public UserDetails addUserDetails(UserDetails user)  {
        return null;
    }

    @Override
    public UserDetails setUserDetails(UserDetails userDetails) {
        return null;
    }

    @Override
    public List<Comment> getComments(MediaContentType type, long itemId)  {
        return null;
    }

    @Override
    public Comment addComment(Comment comment)  {
        return null;
    }

    @Override
    public Like addLike(Like like)  {
        return null;
    }

    @Override
    public void removeLike(long likeId)  {

    }

    @Override
    public List<UserDetails> getLikes(MediaContentType type, long itemId)  {
        return null;
    }
}
