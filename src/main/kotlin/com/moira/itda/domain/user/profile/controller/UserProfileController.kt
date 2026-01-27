package com.moira.itda.domain.user.profile.controller

import com.moira.itda.domain.user.profile.dto.response.UserProfileResponse
import com.moira.itda.domain.user.profile.service.UserProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * 프로필 조회 모달
 */
@RestController
class UserProfileController(
    private val service: UserProfileService
) {
    /**
     * 프로필 조회
     */
    @GetMapping("/api/user/{userId}")
    fun getUserProfile(@PathVariable userId: String): ResponseEntity<UserProfileResponse> {
        val response = service.getUserProfile(userId = userId)

        return ResponseEntity.ok(response)
    }
}