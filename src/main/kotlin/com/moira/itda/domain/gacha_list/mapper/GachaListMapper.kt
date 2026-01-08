package com.moira.itda.domain.gacha_list.mapper

import com.moira.itda.domain.gacha_list.dto.response.GachaListResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaListMapper {
    /**
     * 가챠목록 조회 > totalElements 계산
     */
    fun selectGachaListCnt(keyword: String): Long

    /**
     * 가챠목록 조회
     */
    fun selectGachaList(
        keyword: String,
        pageSize: Int,
        offset: Int,
        sort: String
    ): List<GachaListResponse>
}