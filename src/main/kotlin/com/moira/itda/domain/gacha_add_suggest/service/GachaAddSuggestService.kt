package com.moira.itda.domain.gacha_add_suggest.service

import com.moira.itda.domain.gacha_add_suggest.component.GachaAddSuggestValidator
import com.moira.itda.domain.gacha_add_suggest.dto.request.GachaAddSuggestRequest
import com.moira.itda.domain.gacha_add_suggest.dto.response.MyGachaAddSuggestPageResponse
import com.moira.itda.domain.gacha_add_suggest.mapper.GachaAddSuggestMapper
import com.moira.itda.global.entity.GachaAddSuggest
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaAddSuggestService(
    private val mapper: GachaAddSuggestMapper,
    private val validator: GachaAddSuggestValidator,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
    /**
     * 정보등록요청
     */
    @Transactional
    fun add(userId: String, request: GachaAddSuggestRequest) {
        // [1] 유효성 검사
        validator.validate(request = request)

        // [2] 저장
        val gachaAddSuggest = GachaAddSuggest.fromRequest(userId = userId, request = request)
        mapper.insertGachaAddSuggest(gachaAddSuggest = gachaAddSuggest)
    }

    /**
     * 마이페이지 > 정보등록요청 결과
     */
    @Transactional(readOnly = true)
    fun getAll(userId: String, page: Int): MyGachaAddSuggestPageResponse {
        // [1] 변수 세팅
        val pageSize = PageSizeConstant.MY_GACHA_ADD_SUGGEST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 조회
        val totalElements = mapper.selectGachaAddSuggestListCnt(userId = userId)
        val content = mapper.selectGachaAddSuggestList(
            userId = userId,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 오프셋 페이지네이션 적용
        val pageResponse = offsetPaginationHandler.getPageResponse(
            page = page, pageSize = pageSize, totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return MyGachaAddSuggestPageResponse(content = content, page = pageResponse)
    }
}