package com.moira.itda.domain.target.service

import com.moira.itda.domain.target.dto.response.TargetContentResponse
import com.moira.itda.domain.target.dto.response.TargetGachaResponse
import com.moira.itda.domain.target.dto.response.TargetPageResponse
import com.moira.itda.domain.target.mapper.TargetMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.TRADE_TARGET_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TargetService(
    private val offsetPaginationHandler: OffsetPaginationHandler,
    private val targetMapper: TargetMapper
) {

    /**
     * gachaId로 하위 아이템 목록을 조회한 후, TargetContentResponse 리스트로 변환하는 내부 메서드
     */
    private fun convertToTargetContentResponse(gachaList: List<TargetGachaResponse>): List<TargetContentResponse> {
        return gachaList.map { gacha ->
            val gachaId = gacha.gachaId
            val itemList = targetMapper.selectGachaItemList(gachaId = gachaId)

            TargetContentResponse(gacha = gacha, items = itemList)
        }
    }

    /**
     * 오프셋 페이지네이션을 적용한 TargetPageResponse 객체를 리턴하는 내부 메서드
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
     * 교환 및 판매 대상 지정 모달 > 즐겨찾기 목록
     */
    @Transactional(readOnly = true)
    fun getWishList(userId: String, page: Int): TargetPageResponse {
        // [1] 변수 세팅
        val pageSize = TRADE_TARGET_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 가챠 목록 조회
        val totalElements = targetMapper.selectGachaWishCnt(userId = userId)
        val gachaList = targetMapper.selectGachaWishList(
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
     * 교환 및 판매 대상 지정 모달 > 가챠 목록
     */
    fun getGachaList(keyword: String, page: Int): TargetPageResponse {
        // [1] 변수 세팅
        val pageSize = TRADE_TARGET_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)
        val keywordPattern = if (keyword.isEmpty()) "" else "%${keyword}%"

        // [2] 가챠 목록 조회
        val totalElements = targetMapper.selectGachaCnt(keywordPattern = keywordPattern)
        val gachaList = targetMapper.selectGachaList(
            keywordPattern = keywordPattern,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 하위 아이템 목록 조회
        val contents = this.convertToTargetContentResponse(gachaList = gachaList)

        // [4] 오프셋 기반 페이지네이션 적용 후 리턴
        return this.convertToTargetPageResponse(page = page, totalElements = totalElements, contents = contents)
    }
}