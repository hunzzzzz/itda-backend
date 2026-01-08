package com.moira.itda.domain.gacha.service

import com.moira.itda.domain.gacha.dto.response.*
import com.moira.itda.domain.gacha.mapper.GachaMapper
import com.moira.itda.global.auth.component.CookieHandler
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_WISH_GACHA_LIST_PAGE_SIZE
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.TRADE_TARGET_PAGE_SIZE
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaService(
    private val cookieHandler: CookieHandler,
    private val mapper: GachaMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler,
) {
    /**
     * 가챠정보 > 가챠목록 > 상세정보
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
     * [내부 메서드] gachaId로 하위 아이템 목록을 조회한 후, TargetContentResponse 리스트로 변환
     */
    private fun convertToTargetContentResponse(gachaList: List<TargetGachaResponse>): List<TargetContentResponse> {
        return gachaList.map { gacha ->
            val gachaId = gacha.gachaId
            val itemList = mapper.selectTargetGachaItemList(gachaId = gachaId)

            TargetContentResponse(gacha = gacha, items = itemList)
        }
    }

    /**
     * [내부 메서드] 오프셋 페이지네이션을 적용한 TargetPageResponse 객체를 리턴
     */
    private fun convertToTargetPageResponse(
        page: Int,
        totalElements: Long,
        contents: List<TargetContentResponse>
    ): TargetPageResponse {
        val pageResponse = offsetPaginationHandler.getPageResponse(
            pageSize = TRADE_TARGET_PAGE_SIZE,
            page = page,
            totalElements = totalElements
        )

        return TargetPageResponse(content = contents, page = pageResponse)
    }

    /**
     * 교환/판매 대상 지정 모달 > 가챠목록
     */
    fun getTargetGachaList(keyword: String, page: Int): TargetPageResponse {
        // [1] 변수 세팅
        val pageSize = TRADE_TARGET_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 가챠목록 조회
        val totalElements = mapper.selectGachaListCnt(keyword = keyword)
        val gachaList = mapper.selectTargetGachaList(
            keyword = keyword,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 하위 아이템 목록 조회
        val contents = this.convertToTargetContentResponse(gachaList = gachaList)

        // [4] 오프셋 기반 페이지네이션 적용 후 리턴
        return this.convertToTargetPageResponse(page = page, totalElements = totalElements, contents = contents)
    }

    /**
     * 교환/판매 대상 지정 모달 > 즐겨찾기 가챠목록
     */
    @Transactional(readOnly = true)
    fun getTargetWishGachaList(userId: String, page: Int): TargetPageResponse {
        // [1] 변수 세팅
        val pageSize = TRADE_TARGET_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 가챠목록 조회
        val totalElements = mapper.selectWishGachaListCnt(userId = userId)
        val gachaList = mapper.selectTargetWishGachaList(
            userId = userId,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 하위 아이템 목록 조회
        val contents = this.convertToTargetContentResponse(gachaList = gachaList)

        // [4] 오프셋 기반 페이지네이션 적용 후 리턴
        return this.convertToTargetPageResponse(
            page = page,
            totalElements = totalElements,
            contents = contents
        )
    }

    /**
     * 마이페이지 > 즐겨찾기 > 즐겨찾기 가챠목록
     */
    @Transactional(readOnly = true)
    fun getWishGachaList(userId: String, page: Int): GachaPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_WISH_GACHA_LIST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 가챠목록 조회
        val totalElements = mapper.selectWishGachaListCnt(userId = userId)
        val gachaList = mapper.selectWishGachaList(userId = userId, pageSize = pageSize, offset = offset)

        // [3] 오프셋 기반 페이지네이션 구현
        val pageResponse = offsetPaginationHandler.getPageResponse(
            page = page, pageSize = pageSize, totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return GachaPageResponse(content = gachaList, page = pageResponse)
    }

    /**
     * 거래제안 모달 > 가챠 하위 아이템 목록 조회
     * 교환등록 > 가챠 하위 아이템 목록 조회
     * 판매등록 > 가챠 하위 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getGachaItemList(gachaId: String): List<GachaItemNameResponse> {
        return mapper.selectGachaItemNameList(gachaId = gachaId)
    }
}