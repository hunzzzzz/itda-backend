package com.moira.itda.domain.user_place.controller

import com.moira.itda.domain.user_place.dto.request.UserPlaceRequest
import com.moira.itda.domain.user_place.dto.response.UserPlaceResponse
import com.moira.itda.domain.user_place.service.UserPlaceService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * MY PLACE 모달
 */
@RestController
class UserPlaceController(
    private val service: UserPlaceService
) {
    /**
     * MY PLACE 등록
     */
    @PostMapping("/api/user/place")
    fun add(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: UserPlaceRequest
    ): ResponseEntity<Nothing?> {
        service.add(userId = userAuth.userId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * MY PLACE 목록 조회
     */
    @GetMapping("/api/user/place")
    fun get(@UserPrincipal userAuth: UserAuth): ResponseEntity<List<UserPlaceResponse>> {
        val response = service.getList(userId = userAuth.userId)

        return ResponseEntity.ok(response)
    }

    /**
     * MY PLACE 기본 설정
     */
    @PutMapping("/api/user/place/{placeId}")
    fun setDefault(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable placeId: String
    ): ResponseEntity<Nothing?> {
        service.setDefault(userId = userAuth.userId, placeId = placeId)

        return ResponseEntity.ok(null)
    }


    /**
     * MY PLACE 삭제
     */
    @DeleteMapping("/api/user/place/{placeId}")
    fun delete(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable placeId: String
    ): ResponseEntity<Nothing?> {
        service.delete(userId = userAuth.userId, placeId = placeId)

        return ResponseEntity.noContent().build()
    }
}