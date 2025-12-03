package com.moira.itda.domain.profile.dto.response;

public record ProfileResponse(
        String userId,
        String role,
        String status,
        String type,
        String name,
        String nickname,
        String phone,
        String imageUrl,
        String email,
        String lastLoginAt,
        String createdAt
) {
}
