package com.plapp.apigateway.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


public class SessionTokenMapping {

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SessionToken {
        @Id
        @GeneratedValue
        private Long sessionId;

        @Column(length = 2048)
        private String sessionToken;

        @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
        JwtToken jwt;
    }


    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class JwtToken {
        @Id
        private Long userId;

        @Column(columnDefinition = "TEXT")
        private String jwt;
    }
}
