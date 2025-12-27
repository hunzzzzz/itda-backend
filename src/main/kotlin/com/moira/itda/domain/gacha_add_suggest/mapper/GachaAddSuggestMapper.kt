package com.moira.itda.domain.gacha_add_suggest.mapper

import com.moira.itda.global.entity.GachaAddSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaAddSuggestMapper {
    /**
     * 정보등록요청 > GachaAddSuggest 저장
     */
    fun insertGachaAddSuggest(gachaAddSuggest: GachaAddSuggest)
}