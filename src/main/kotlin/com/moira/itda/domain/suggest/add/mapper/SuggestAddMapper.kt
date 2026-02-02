package com.moira.itda.domain.suggest.add.mapper

import com.moira.itda.global.entity.TradeSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestAddMapper {
    /**
     * 구매제안 > TradeSuggest 저장
     * 교환제안 > TradeSuggest 저장
     */
    fun insertTradeSuggest(tradeSuggest: TradeSuggest)
}