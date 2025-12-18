package com.moira.itda.domain.gacha.detail.service

import com.moira.itda.domain.gacha.detail.dto.response.GachaDetailResponse
import com.moira.itda.domain.gacha.detail.dto.response.GachaWishCheckResponse
import com.moira.itda.domain.gacha.detail.mapper.GachaDetailMapper
import com.moira.itda.global.auth.component.CookieHandler
import com.moira.itda.global.entity.GachaWish
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

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

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 즐겨찾기 여부 조회
     */
    @Transactional(readOnly = true)
    fun checkWish(userId: String, gachaId: String): GachaWishCheckResponse {
        val wishYn = gachaDetailMapper.selectGachaWishChk(userId = userId, gachaId = gachaId)

        return GachaWishCheckResponse(wishYn = wishYn)
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 즐겨찾기
     */
    @Transactional
    fun wish(userId: String, gachaId: String) {
        // [1] 즐겨찾기 여부 조회
        val wishYn = this.checkWish(userId = userId, gachaId = gachaId).wishYn

        // [2-1] GachaWish 저장
        if ("N" == wishYn) {
            val gachaWish = GachaWish(id = null, userId = userId, gachaId = gachaId, createdAt = ZonedDateTime.now())
            gachaDetailMapper.insertGachaWish(gachaWish = gachaWish)
        }
        // [2-2] GachaWish 삭제
        else {
            gachaDetailMapper.deleteGachaWish(userId = userId, gachaId = gachaId)
        }
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 교환 > 진행 중인 교환글 존재 여부 확인
     */
    @Transactional(readOnly = true)
    fun checkExchange(userId: String, gachaId: String) {
        if (gachaDetailMapper.selectExchangeChk(userId = userId, gachaId = gachaId) > 0) {
            throw ItdaException(ErrorCode.ALREADY_PENDING_EXCHANGE)
        }
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 판매 > 진행 중인 판매글 존재 여부 확인
     */
    @Transactional(readOnly = true)
    fun checkSales(userId: String, gachaId: String) {
        if (gachaDetailMapper.selectSalesChk(userId = userId, gachaId = gachaId) > 0) {
            throw ItdaException(ErrorCode.ALREADY_PENDING_SALES)
        }
    }

}