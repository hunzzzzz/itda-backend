package com.moira.itda.domain.info.controller

import com.moira.itda.domain.info.dto.request.GachaInfoAddRequest
import com.moira.itda.domain.info.service.GachaInfoAddService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 정보등록요청 탭
 */
@RestController
class GachaInfoAddController(
    private val gachaInfoAddService: GachaInfoAddService
) {
    /**
     * 정보등록요청 탭 > 정보등록요청
     */
    @PostMapping("/api/gacha/info/add")
    fun add(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: GachaInfoAddRequest
    ): ResponseEntity<Nothing> {
        gachaInfoAddService.add(userId = userAuth.userId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }
}