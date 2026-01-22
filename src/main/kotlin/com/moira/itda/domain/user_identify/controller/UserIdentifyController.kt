package com.moira.itda.domain.user_identify.controller

import com.moira.itda.domain.user_identify.dto.request.IdentifyCheckRequest
import com.moira.itda.domain.user_identify.dto.request.IdentifyRequest
import com.moira.itda.domain.user_identify.dto.response.IdentifyInfoResponse
import com.moira.itda.domain.user_identify.service.UserIdentifyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 본인인증
 */
@RestController
class UserIdentifyController(
    private val service: UserIdentifyService
) {
    /**
     * 본인인증
     */
    @PostMapping("/api/identify")
    fun identify(
        @RequestBody request: IdentifyRequest
    ): ResponseEntity<Nothing?> {
        service.identify(request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 본인인증 > 인증코드 확인
     */
    // TODO: 추후 본인인증 절차가 PASS 인증으로 변경되면 해당 인증코드 확인 로직은 사용하지 않는다.
    @PostMapping("/api/identify/check/code")
    fun checkIdentifyCode(
        @RequestBody request: IdentifyCheckRequest
    ): ResponseEntity<IdentifyInfoResponse?> {
        val response = service.checkIdentifyCode(request = request)

        return ResponseEntity.ok(response)
    }
}