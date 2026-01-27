package com.moira.itda.domain.user.myplace.controller

import com.moira.itda.domain.user.myplace.dto.request.MyPlaceRequest
import com.moira.itda.domain.user.myplace.dto.response.MyPlaceResponse
import com.moira.itda.domain.user.myplace.service.MyPlaceService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * MY PLACE 모달
 */
@RestController
class MyPlaceController(
    private val service: MyPlaceService
) {
    /**
     * MY PLACE 등록
     */
    @PostMapping("/api/me/place")
    fun add(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: MyPlaceRequest
    ): ResponseEntity<Nothing?> {
        service.add(userId = userAuth.userId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * MY PLACE 목록 조회
     */
    @GetMapping("/api/me/place")
    fun get(@UserPrincipal userAuth: UserAuth): ResponseEntity<List<MyPlaceResponse>> {
        val response = service.getList(userId = userAuth.userId)

        return ResponseEntity.ok(response)
    }

    /**
     * MY PLACE 기본 설정
     */
    @PutMapping("/api/me/place/{placeId}")
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
    @DeleteMapping("/api/me/place/{placeId}")
    fun delete(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable placeId: String
    ): ResponseEntity<Nothing?> {
        service.delete(userId = userAuth.userId, placeId = placeId)

        return ResponseEntity.noContent().build()
    }
}