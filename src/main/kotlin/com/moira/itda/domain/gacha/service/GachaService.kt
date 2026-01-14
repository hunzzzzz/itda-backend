package com.moira.itda.domain.gacha.service

import com.moira.itda.domain.gacha.dto.response.GachaDetailResponse
import com.moira.itda.domain.gacha.mapper.GachaMapper
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
class GachaService(
    private val cookieHandler: CookieHandler,
    private val mapper: GachaMapper
) {
    /**
     * 가챠상세정보
     */
    @Transactional
    fun getGacha(
        userId: String,
        gachaId: String,
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): GachaDetailResponse {
        // [1] 상세정보 조회
        val gacha = mapper.selectGacha(userId = userId, gachaId = gachaId)
            ?: throw ItdaException(ErrorCode.GACHA_NOT_FOUND)
        val items = mapper.selectGachaItemList(gachaId = gachaId)
        val wishYn = mapper.selectGachaWishChk(userId = userId, gachaId = gachaId)
        val pickedItems = mapper.selectGachaPickHistoryList(gachaId = gachaId, userId = userId)
        val places = mapper.selectUserPlaceList(userId = userId)

        // [2] 쿠키에 GachaId 여부 확인
        if (!cookieHandler.checkGachaIdInCookie(gachaId = gachaId, request = httpReq)) {
            // [2-1] 쿠키에 GachaId가 없다면 조회수 증가
            mapper.updateViewCount(gachaId = gachaId)

            // [2-2] 쿠키에 GachaId 추가 (조회수 체크용)
            cookieHandler.putGachaIdInCookie(gachaId = gachaId, response = httpRes)
        }

        // [3] 상세정보 리턴
        return GachaDetailResponse(
            gacha = gacha,
            items = items,
            myWishYn = wishYn,
            myPickedItems = pickedItems,
            myPlaces = places
        )
    }

    /**
     * 가챠상세정보 > 즐겨찾기
     */
    @Transactional
    fun wish(userId: String, gachaId: String) {
        // [1] 즐겨찾기 여부 조회
        val wishYn = mapper.selectGachaWishChk(userId = userId, gachaId = gachaId)

        // [2-1] GachaWish 저장
        if ("N" == wishYn) {
            val gachaWish = GachaWish(id = null, userId = userId, gachaId = gachaId, createdAt = ZonedDateTime.now())
            mapper.insertGachaWish(gachaWish = gachaWish)
        }
        // [2-2] GachaWish 삭제
        else {
            mapper.deleteGachaWish(userId = userId, gachaId = gachaId)
        }
    }
}