package com.moira.itda.domain.suggest.reject.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestRejectMapper {
    /**
     * 제안거절 > TradeSuggest의 status 변경 (REJECTED)
     */
    fun updateTradeSuggestStatusRejected(suggestId: String)
}