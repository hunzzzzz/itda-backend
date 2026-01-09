package com.moira.itda.domain.trade_target.service

import com.moira.itda.domain.trade_target.dto.response.TargetContentResponse
import com.moira.itda.domain.trade_target.dto.response.TargetGachaResponse
import com.moira.itda.domain.trade_target.dto.response.TargetPageResponse
import com.moira.itda.domain.trade_target.mapper.TradeTargetMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.TRADE_TARGET_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeTargetService(
    private val mapper: TradeTargetMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * [내부 메서드] gachaId로 하위 아이템 목록을 조회한 후, TargetContentResponse 리스트로 변환
     */
    private fun convertToTargetContentResponse(gachaList: List<TargetGachaResponse>): List<TargetContentResponse> {
        return gachaList.map { gacha ->
            val gachaId = gacha.gachaId
            val itemList = mapper.selectGachaItemList(gachaId = gachaId)

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
        val pageResponse = pageHandler.getPageResponse(
            pageSize = TRADE_TARGET_PAGE_SIZE,
            page = page,
            totalElements = totalElements
        )

        return TargetPageResponse(content = contents, page = pageResponse)
    }

    /**
     * 거래대상 가챠지정 모달 > 즐겨찾기 목록
     */
    @Transactional(readOnly = true)
    fun getWishList(userId: String, page: Int): TargetPageResponse {
        // [1] 변수 세팅
        val pageSize = TRADE_TARGET_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 가챠목록 조회
        val totalElements = mapper.selectGachaWishListCnt(userId = userId)
        val gachaList = mapper.selectGachaWishList(
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
     * 거래대상 가챠지정 모달 > 가챠목록
     */
    fun getGachaList(keyword: String, page: Int): TargetPageResponse {
        // [1] 변수 세팅
        val pageSize = TRADE_TARGET_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 가챠목록 조회
        val totalElements = mapper.selectGachaListCnt(keyword = keyword)
        val gachaList = mapper.selectGachaList(
            keyword = keyword,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 하위 아이템 목록 조회
        val contents = this.convertToTargetContentResponse(gachaList = gachaList)

        // [4] 오프셋 기반 페이지네이션 적용 후 리턴
        return this.convertToTargetPageResponse(page = page, totalElements = totalElements, contents = contents)
    }
}