package com.moira.itda.global.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class User {
    private String id;
    private UserRole role;
    private UserStatus status;
    private UserType type;
    private String name;
    private String nickname;
    private String phone;
    private String imageUrl;
    private String email;
    private String password;
    private String refreshToken;
    private ZonedDateTime lastLoginAt;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
