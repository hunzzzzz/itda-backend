package com.moira.itda.domain.user_gacha_wish_list.service

import com.moira.itda.domain.gacha.dto.response.GachaPageResponse
import com.moira.itda.domain.user_gacha_wish_list.dto.response.WishGachaPageResponse
import com.moira.itda.domain.user_gacha_wish_list.mapper.UserGachaWishMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_WISH_GACHA_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserGachaWishService(
    private val mapper: UserGachaWishMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 즐겨찾기 가챠목록 조회
     */
    @Transactional(readOnly = true)
    fun getWishGachaList(userId: String, page: Int): WishGachaPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_WISH_GACHA_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 가챠목록 조회
        val totalElements = mapper.selectWishGachaListCnt(userId = userId)
        val gachaList = mapper.selectWishGachaList(userId = userId, pageSize = pageSize, offset = offset)

        // [3] 오프셋 기반 페이지네이션 구현
        val pageResponse = pageHandler.getPageResponse(
            page = page, pageSize = pageSize, totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return WishGachaPageResponse(content = gachaList, page = pageResponse)
    }
}