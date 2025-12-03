package com.moira.itda.global.auth;

public record SimpleUserAuth(
        String userId,
        String email,
        String nickname,
        String role
) {
}
