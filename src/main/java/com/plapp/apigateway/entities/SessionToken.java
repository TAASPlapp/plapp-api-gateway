package com.plapp.apigateway.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionToken {
    @Id
    @Column(length = 2048)
    private String sessionToken;

    @Column(length = 2048)
    private String jwt;
}
