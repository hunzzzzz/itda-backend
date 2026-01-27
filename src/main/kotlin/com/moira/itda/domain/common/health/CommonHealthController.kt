package com.moira.itda.domain.common.health

import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CommonHealthController {
    /**
     * Health Check (non-Auth)
     */
    @GetMapping("/api/health")
    fun healthCheck(): ResponseEntity<Nothing?> {
        return ResponseEntity.ok(null)
    }

    /**
     * Health Check (Auth)
     */
    @GetMapping("/api/me/simple")
    fun getMyProfile(@UserPrincipal userAuth: UserAuth): ResponseEntity<UserAuth> {
        return ResponseEntity.ok(userAuth)
    }
}