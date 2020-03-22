package com.plapp.apigateway.services.mockservices;

import com.plapp.apigateway.services.SocialService;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.entities.utils.ApiResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lists.utils.Lists;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public ApiResponse updateUserDetails(UserDetails userDetails) throws Exception {
        return new ApiResponse();
    }

    @Override
    public UserDetails setUserDetails(UserDetails userDetails) throws Exception {
        return userDetails;
    }

    @Override
    public UserDetails addUserDetails(UserDetails user) throws Exception {
        return user;
    }

    @Override
    public List<Comment> getComments(MediaContentType type, long itemId) throws Exception {
        JSONParser parser = new JSONParser();
        File jsonFile = new ClassPathResource("mock-response/mock-comments.json").getFile();
        JSONArray jsonComments = (JSONArray)parser.parse(new FileReader(jsonFile));

        List<Comment> comments = Lists.map(
                jsonComments.iterator(),
                this::jsonToComment
        );

        return Lists.filter(
                comments,
                c -> c.getMediaContentType() == type && c.getItemId() == itemId
        );
    }

    @Override
    public List<UserDetails> getLikes(MediaContentType type, long itemId) throws Exception {
        List<UserDetails> users = new ArrayList<>();
        users.add(getUserDetails(2));
        users.add(getUserDetails(3));
        users.add(getUserDetails(4));
        return users;
    }

    @Override
    public Comment addComment(Comment comment) throws Exception {
        return comment;
    }

    @Override
    public ApiResponse removeLike(long likeId) throws Exception {
        return null;
    }
    
    @Override //da controllare
    public Like addLike(Like like) throws Exception {
        return like;
    }

}
