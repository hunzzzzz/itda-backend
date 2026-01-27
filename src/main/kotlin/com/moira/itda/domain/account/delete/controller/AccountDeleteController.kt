package com.moira.itda.domain.account.delete.controller

import com.moira.itda.domain.account.delete.service.AccountDeleteService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountDeleteController(
    private val service: AccountDeleteService
) {
    /**
     * 회원탈퇴
     */
    @DeleteMapping("/api/me/delete")
    fun delete(
        @UserPrincipal userAuth: UserAuth,
        httpRes: HttpServletResponse
    ): ResponseEntity<Nothing> {
        service.delete(userId = userAuth.userId, httpRes = httpRes)

        return ResponseEntity.ok(null)
    }
}