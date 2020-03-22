package com.plapp.apigateway.services.restservices;

import com.plapp.apigateway.services.SocialService;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@ConfigurationProperties(prefix = "services.social")
public class RestSocialService implements SocialService {
    private String baseAddress;

    public void setServiceAddress(String serviceAddress) {
        this.baseAddress = serviceAddress;
    }

    @Override
    public UserDetails getUserDetails(long userId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(baseAddress + "/social/user/" + userId, UserDetails.class);
    }

    @Override
    //qui tornare l'oggetto apiResponse
    public UserDetails addUserDetails(UserDetails user) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseAddress + "/user/" + user.getUserId() + "/add", user, UserDetails.class);
    }

    @Override
    public UserDetails setUserDetails(UserDetails userDetails) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseAddress + "/user/" + userDetails.getUserId() + "/update", userDetails, UserDetails.class);
    }

    @Override
    public List<Comment> getComments(MediaContentType type, long itemId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                baseAddress + "/comment/" + itemId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Comment>>() {},
                type
        ).getBody();
    }

    @Override
    public Comment addComment(Comment comment) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseAddress + "/comment/" + comment.getId() + "/add", comment, Comment.class);
    }

    @Override
    public Like addLike(Like like) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseAddress + "/like/" + like.getId() + "/add", like, Like.class);
    }

    @Override
    public void removeLike(long likeId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(baseAddress + "/like/" + likeId + "/remove", Void.class);
    }

    @Override
    public List<UserDetails> getLikes(MediaContentType type, long itemId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                baseAddress + "/like/" + itemId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDetails>>() {},
                type
        ).getBody();
    }
}
