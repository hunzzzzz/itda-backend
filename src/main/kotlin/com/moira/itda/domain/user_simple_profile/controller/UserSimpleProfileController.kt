package com.moira.itda.domain.user_simple_profile.controller

import com.moira.itda.domain.user_simple_profile.dto.response.UserSimpleProfileResponse
import com.moira.itda.domain.user_simple_profile.service.UserSimpleProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * 심플 프로필 조회 모달
 */
@RestController
class UserSimpleProfileController(
    private val service: UserSimpleProfileService
) {
    /**
     * 심플 프로필 조회
     */
    @GetMapping("/api/user/{userId}/simple/profile")
    fun getSimpleUserProfile(@PathVariable userId: String): ResponseEntity<UserSimpleProfileResponse> {
        val response = service.getSimpleUserProfile(userId = userId)

        return ResponseEntity.ok(response)
    }
}