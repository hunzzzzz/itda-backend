package com.moira.itda.domain.gacha.list.mapper

import com.moira.itda.domain.gacha.list.dto.response.GachaResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaListMapper {
    /**
     * 가챠정보 > 가챠목록 > 전체 목록 조회 > totalElements 계산
     */
    fun selectGachaCnt(keywordPattern: String): Long

    /**
     * 가챠정보 > 가챠목록 > 전체 목록 조회
     */
    fun selectGachaList(keywordPattern: String, pageSize: Int, offset: Int): List<GachaResponse>
}