package com.moira.itda.domain.common

import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping


class MySimpleProfileController {
    /**
     * 공통 > 내 프로필 조회
     */
    @GetMapping("/api/me/simple")
    fun getMyProfileSimple(@UserPrincipal userAuth: UserAuth): ResponseEntity<UserAuth?> {
        return ResponseEntity.ok(userAuth)
    }
}