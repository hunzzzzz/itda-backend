package com.moira.itda.domain.gacha.detail.mapper

import com.moira.itda.domain.gacha.detail.dto.response.GachaResponse
import com.moira.itda.domain.gacha.detail.dto.response.GachaItemResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaDetailMapper {
    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 가챠 조회
     */
    fun getGacha(gachaId: String): GachaResponse?

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 아이템 목록 조회
     */
    fun getGachaItemList(gachaId: String): List<GachaItemResponse>

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 조회수 증가
     */
    fun updateViewCount(gachaId: String)
}