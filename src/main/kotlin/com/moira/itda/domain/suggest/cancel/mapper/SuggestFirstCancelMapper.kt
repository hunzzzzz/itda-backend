package com.moira.itda.domain.suggest.cancel.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestFirstCancelMapper {
    /**
     * 제안취소 (판매자 응답 전) > TradeSuggest의 status 변경 (DELETED)
     */
    fun updateTradeSuggestStatusDeleted(suggestId: String)
}