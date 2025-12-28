package com.moira.itda.domain.gacha.mapper

import com.moira.itda.domain.gacha.dto.response.GachaItemResponse
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

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 상세정보 조회
     */
    fun selectGacha(gachaId: String): GachaResponse?

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<GachaItemResponse>

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 조회수 증가
     */
    fun updateViewCount(gachaId: String)
}