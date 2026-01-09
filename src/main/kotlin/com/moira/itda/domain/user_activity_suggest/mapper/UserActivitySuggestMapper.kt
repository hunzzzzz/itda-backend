package com.moira.itda.domain.user_activity_suggest.mapper

import com.moira.itda.domain.user_activity_suggest.dto.response.MyTradeSuggestResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserActivitySuggestMapper {
    /**
     * 내 활동 > 제안 > 내 제안목록 조회 > totalElements 계산
     */
    fun selectMyTradeSuggestListCnt(userId: String): Long

    /**
     * 내 활동 > 제안 > 내 제안목록 조회
     */
    fun selectMyTradeSuggestList(
        userId: String,
        pageSize: Int,
        offset: Int
    ): List<MyTradeSuggestResponse>

    /**
     * 내 활동 > 제안 > 제안취소 > TradeSuggest 정보 조회
     */
    fun selectTradeSuggestInfo(suggestId: String): HashMap<String, String?>

    /**
     * 내 활동 > 제안 > 제안취소 > TradeSuggest status 변경 (CANCELED_BEFORE_RESPONSE)
     */
    fun updateTradeSuggestStatusCBR(suggestId: String)

    /**
     * 내 활동 > 제안 > 제안삭제 > TradeSuggest status 변경 (DELETED)
     */
    fun updateTradeSuggestStatusDeleted(suggestId: String)
}