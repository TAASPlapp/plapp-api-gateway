package com.plapp.apigateway.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


public class SessionTokenMapping {

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SessionToken {
        @Id
        @Column(length = 2048)
        private String sessionToken;

        @ManyToOne
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

        @Column(length = 2048)
        private String jwt;
    }
}
