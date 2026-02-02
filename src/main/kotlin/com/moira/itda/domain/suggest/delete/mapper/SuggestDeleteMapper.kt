package com.moira.itda.domain.suggest.delete.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestDeleteMapper {
    /**
     * 제안삭제 > TradeSuggest status 변경 (DELETED)
     */
    fun updateTradeSuggestStatusDeleted(suggestId: String)
}