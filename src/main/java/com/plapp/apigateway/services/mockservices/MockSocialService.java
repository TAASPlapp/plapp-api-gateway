package com.plapp.apigateway.services.mockservices;

import com.plapp.apigateway.controllers.ApiResponse;
import com.plapp.apigateway.services.SocialService;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import org.apache.catalina.User;
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

    @Override
    public UserDetails getUserDetails(long userId) throws Exception {
        UserDetails userDetails = new UserDetails(userId);
        userDetails.setUsername("valka-getz");
        userDetails.setFirstName("Valka");
        userDetails.setLastName("Getz");
        userDetails.setBio("Valka Getz is a 25-year-old sports science student who enjoys eating out, escapology and horse riding. ECCETERA ECCETERA");
        userDetails.setBirthdate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2020-02-14T12:56:00"));
        userDetails.setProfilePicture("https://source.unsplash.com/bXq8pVfP-fY/60x60");
        return userDetails;
    }


    @Override
    public ApiResponse setUserDetails(UserDetails userDetails) throws Exception {
        return new ApiResponse();
    }

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
    public ApiResponse addComment(Comment comment) throws Exception {
        return new ApiResponse();
    }

    @Override
    public ApiResponse like(MediaContentType type, long itemId) throws Exception {
        return null;
    }

    @Override
    public ApiResponse unlike(MediaContentType type, long itemId) throws Exception {
        return null;
    }

    @Override
    public List<UserDetails> getLikes(MediaContentType type, long itemId) throws Exception {
        List<UserDetails> users = new ArrayList<>();
        users.add(getUserDetails(2));
        users.add(getUserDetails(3));

        return users;
    }
}
