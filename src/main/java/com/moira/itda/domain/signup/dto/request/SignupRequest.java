package com.moira.itda.domain.signup.dto.request;

public record SignupRequest(
        String name,
        String nickname,
        String phone,
        String email,
        String password
) {
}
