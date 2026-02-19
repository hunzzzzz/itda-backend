package com.moira.itda.domain.user.compliment.controller

import com.moira.itda.domain.user.compliment.dto.request.ComplimentRequest
import com.moira.itda.domain.user.compliment.service.UserComplimentService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 내 활동 > 완료된 거래
 */
@RestController
class UserComplimentController(
    private val service: UserComplimentService
) {
    /**
     * 유저칭찬
     */
    @PostMapping("/api/users/compliment")
    fun compliment(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: ComplimentRequest
    ): ResponseEntity<Nothing?> {
        service.compliment(userId = userAuth.userId, request = request)

        return ResponseEntity.ok().body(null)
    }
}