package com.moira.itda.domain.gacha.detail.service

import com.moira.itda.domain.gacha.detail.dto.response.GachaDetailResponse
import com.moira.itda.domain.gacha.detail.mapper.GachaDetailMapper
import com.moira.itda.global.auth.component.CookieHandler
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaDetailService(
    private val cookieHandler: CookieHandler,
    private val gachaDetailMapper: GachaDetailMapper
) {
    /**
     * 가챠정보 > 가챠목록 > 상세정보
     */
    @Transactional
    fun get(
        gachaId: String,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): GachaDetailResponse {
        // [1] 상세정보 조회
        val gacha = gachaDetailMapper.getGacha(gachaId = gachaId)
            ?: throw ItdaException(ErrorCode.GACHA_NOT_FOUND)
        val items = gachaDetailMapper.getGachaItemList(gachaId = gachaId)

        // [2] 쿠키에 GachaId 여부 확인
        if (!cookieHandler.checkGachaIdInCookie(gachaId = gachaId, request = httpServletRequest)) {
            // [2-1] 쿠키에 GachaId가 없다면 조회수 증가
            gachaDetailMapper.updateViewCount(gachaId = gachaId)

            // [2-2] 쿠키에 GachaId 추가 (조회수 체크용)
            cookieHandler.putGachaIdInCookie(gachaId = gachaId, response = httpServletResponse)
        }

        // [3] 상세정보 리턴
        return GachaDetailResponse(gacha = gacha, items = items)
    }
}