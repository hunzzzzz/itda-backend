package com.moira.itda.domain.admin.user.controller

import com.moira.itda.domain.admin.user.dto.request.UserBanRequest
import com.moira.itda.domain.admin.user.dto.response.AdminUserBanResponse
import com.moira.itda.domain.admin.user.dto.response.AdminUserResponse
import com.moira.itda.domain.admin.user.service.AdminUserService
import com.moira.itda.global.auth.aop.IsAdmin
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdminUserController(
    private val adminUserService: AdminUserService
) {
    /**
     * 어드민 페이지 > 유저 > 유저 목록 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/users")
    fun getUsers(): ResponseEntity<List<AdminUserResponse>> {
        val response = adminUserService.getUsers()

        return ResponseEntity.ok(response)
    }

    /**
     * 어드민 페이지 > 유저 > 계정 정지
     */
    @IsAdmin
    @PutMapping("/api/admin/users/{userId}/ban")
    fun ban(@PathVariable userId: String, @RequestBody request: UserBanRequest): ResponseEntity<Nothing> {
        adminUserService.ban(userId = userId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 어드민 페이지 > 유저 > 계정 정지 > 계정 정지 이력 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/users/{userId}/ban")
    fun getBanHistories(
        @PathVariable userId: String
    ): ResponseEntity<List<AdminUserBanResponse>> {
        val response = adminUserService.getBanHistories(userId = userId)

        return ResponseEntity.ok(response)
    }

    /**
     * 어드민 페이지 > 유저 > 어드민 권한 부여
     */
    @PutMapping("/api/admin/users/{userId}/admin")
    fun giveAdmin(@PathVariable userId: String): ResponseEntity<Nothing> {
        adminUserService.giveAdmin(userId = userId)

        return ResponseEntity.ok(null)
    }
}