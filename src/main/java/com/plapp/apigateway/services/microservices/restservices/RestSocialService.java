package com.plapp.apigateway.services.microservices.restservices;

import com.plapp.apigateway.services.microservices.Authorities;
import com.plapp.apigateway.services.microservices.AuthorizationService;
import com.plapp.apigateway.services.microservices.SocialService;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "services.social")
public class RestSocialService implements SocialService {
    @Value("${services.social.serviceAddress}")
    private String baseAddress;

    private final AuthorizationService authorizationService;

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public UserDetails getUserDetails(long userId) {
        return restTemplate.getForObject(baseAddress + "/social/user/" + userId, UserDetails.class);
    }

    @Override
    public UserDetails addUserDetails(UserDetails user) {
        return restTemplate.postForObject(baseAddress + "/social/user/" + user.getUserId() + "/add", user, UserDetails.class);
    }

    @Override
    public UserDetails setUserDetails(UserDetails userDetails) {
        return restTemplate.postForObject(baseAddress + "/social/user/" + userDetails.getUserId() + "/update", userDetails, UserDetails.class);
    }

    @Override
    public List<Comment> getComments(MediaContentType type, long itemId) {
        return restTemplate.exchange(
                baseAddress + "/social/comment/" + itemId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Comment>>() {},
                type
        ).getBody();
    }

    @Override
    public Comment addComment(Comment comment) {
        Comment addedComment = restTemplate.postForObject(baseAddress + "/social/comment/" + comment.getId() + "/add", comment, Comment.class);
        authorizationService.updateAuthorization(Authorities.SOCIAL_COMMENT, addedComment.getId());
        return addedComment;
    }

    @Override
    public Like addLike(Like like) {
        Like addedLike = restTemplate.postForObject(baseAddress + "/social/like/" + like.getId() + "/add", like, Like.class);
        authorizationService.updateAuthorization(Authorities.SOCIAL_LIKE, addedLike.getId());
        return addedLike;
    }

    @Override
    public void removeLike(long likeId) {
        restTemplate.getForObject(baseAddress + "/social/like/" + likeId + "/remove", Void.class);
    }

    @Override
    public List<UserDetails> getLikes(MediaContentType type, long itemId) {
        return restTemplate.exchange(
                baseAddress + "/social/like/" + itemId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDetails>>() {},
                type
        ).getBody();
    }
}
