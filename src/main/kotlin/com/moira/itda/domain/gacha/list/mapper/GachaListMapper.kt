package com.moira.itda.domain.gacha.list.mapper

import com.moira.itda.domain.gacha.list.dto.response.GachaListResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaListMapper {
    /**
     * 가챠목록 조회 > totalElements 계산
     */
    fun selectGachaListCnt(userId: String, keyword: String, showMyWish: String): Long

    /**
     * 가챠목록 조회
     */
    fun selectGachaList(
        userId: String,
        keyword: String,
        pageSize: Int,
        offset: Int,
        sort: String,
        showMyWish: String
    ): List<GachaListResponse>
}