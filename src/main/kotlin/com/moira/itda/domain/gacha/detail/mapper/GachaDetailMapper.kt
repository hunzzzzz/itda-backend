package com.moira.itda.domain.gacha.detail.mapper

import com.moira.itda.domain.gacha.detail.dto.response.GachaResponse
import com.moira.itda.domain.gacha.detail.dto.response.GachaItemResponse
import com.moira.itda.global.entity.GachaWish
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

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 즐겨찾기 여부 조회
     */
    fun selectGachaWishChk(userId: String, gachaId: String): String

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 즐겨찾기 > GachaWish 저장
     */
    fun insertGachaWish(gachaWish: GachaWish)

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 즐겨찾기 > GachaWish 삭제
     */
    fun deleteGachaWish(userId: String, gachaId: String)

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 교환 > 진행 중인 교환글 존재 여부 확인
     */
    fun selectExchangeChk(userId: String, gachaId: String): Int

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 판매 > 진행 중인 판매글 존재 여부 확인
     */
    fun selectSalesChk(userId: String, gachaId: String): Int
}