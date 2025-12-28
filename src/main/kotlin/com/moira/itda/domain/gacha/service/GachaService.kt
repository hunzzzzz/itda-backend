package com.moira.itda.domain.gacha.service

import com.moira.itda.domain.gacha.component.GachaValidator
import com.moira.itda.domain.gacha.dto.response.GachaPageResponse
import com.moira.itda.domain.gacha.mapper.GachaMapper
import com.moira.itda.domain.gacha.dto.response.GachaDetailResponse
import com.moira.itda.global.auth.component.CookieHandler
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.GACHA_LIST_PAGE_SIZE
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaService(
    private val cookieHandler: CookieHandler,
    private val mapper: GachaMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler,
    private val validator: GachaValidator,
) {
    /**
     * 가챠정보 > 가챠 목록
     */
    @Transactional(readOnly = true)
    fun getAll(keyword: String, page: Int, sort: String): GachaPageResponse {
        // [1] 정렬 조건에 대한 유효성 검사
        validator.validateSort(sort = sort)

        // [2] 변수 세팅
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = GACHA_LIST_PAGE_SIZE)

        // [3] 조회
        val totalElements = mapper.selectGachaListCnt(keyword = keyword)
        val contents = mapper.selectGachaList(
            keyword = keyword,
            pageSize = GACHA_LIST_PAGE_SIZE,
            offset = offset,
            sort = sort
        )

        // [4] 오프셋 페이지네이션 구현
        val pageResponse = offsetPaginationHandler.getPageResponse(
            page = page,
            pageSize = GACHA_LIST_PAGE_SIZE,
            totalElements = totalElements
        )

        // [5] DTO 병합 후 리턴
        return GachaPageResponse(content = contents, page = pageResponse)
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보
     */
    @Transactional
    fun get(
        userId: String,
        gachaId: String,
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): GachaDetailResponse {
        // [1] 상세정보 조회
        val gacha = mapper.selectGacha(gachaId = gachaId)
            ?: throw ItdaException(ErrorCode.GACHA_NOT_FOUND)
        val items = mapper.selectGachaItemList(gachaId = gachaId)
        val pickedItems = mapper.selectGachaPickHistoryList(gachaId = gachaId, userId = userId)

        // [2] 쿠키에 GachaId 여부 확인
        if (!cookieHandler.checkGachaIdInCookie(gachaId = gachaId, request = httpReq)) {
            // [2-1] 쿠키에 GachaId가 없다면 조회수 증가
            mapper.updateViewCount(gachaId = gachaId)

            // [2-2] 쿠키에 GachaId 추가 (조회수 체크용)
            cookieHandler.putGachaIdInCookie(gachaId = gachaId, response = httpRes)
        }

        // [3] 상세정보 리턴
        return GachaDetailResponse(gacha = gacha, items = items, pickedItems = pickedItems)
    }
}