package com.moira.itda.domain.gacha.mapper

import com.moira.itda.domain.gacha.dto.response.GachaResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaMapper {
    /**
     * 가챠정보 > 가챠 목록 > 가챠 목록 조회 > totalElements 계산
     */
    fun selectGachaListCnt(keyword: String): Long

    /**
     * 가챠정보 > 가챠 목록 > 가챠 목록 조회
     */
    fun selectGachaList(
        keyword: String,
        pageSize: Int,
        offset: Int,
        sort: String
    ): List<GachaResponse>
}