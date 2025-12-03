package com.moira.itda.global.entity;

import com.moira.itda.domain.signup.dto.request.SignupRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.UUID;

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

    public static User fromSignupRequest(SignupRequest request, PasswordEncoder passwordEncoder) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .type(UserType.NORMAL)
                .name(request.name())
                .nickname(request.nickname())
                .phone(request.phone())
                .imageUrl(null)
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .refreshToken(null)
                .lastLoginAt(null)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
