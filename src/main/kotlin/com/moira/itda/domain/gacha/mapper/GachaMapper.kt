package com.moira.itda.domain.gacha.mapper

import com.moira.itda.domain.gacha.dto.response.GachaItemResponse
import com.moira.itda.domain.gacha.dto.response.GachaResponse
import com.moira.itda.domain.gacha.dto.response.MyPickedItemResponse
import com.moira.itda.domain.gacha.dto.response.MyPlaceResponse
import com.moira.itda.global.entity.GachaWish
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaMapper {
    /**
     * 가챠상세정보 > 가챠 조회
     */
    fun selectGacha(userId: String, gachaId: String): GachaResponse?

    /**
     * 가챠상세정보 > 가챠 아이템목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<GachaItemResponse>

    /**
     * 가챠상세정보 > 내 즐겨찾기 여부 조회
     * 가챠상세정보 > 즐겨찾기 > 내 즐겨찾기 여부 조회
     */
    fun selectGachaWishChk(userId: String, gachaId: String): String

    /**
     * 가챠상세정보 > 내 가챠이력 조회
     */
    fun selectGachaPickHistoryList(gachaId: String, userId: String): List<MyPickedItemResponse>

    /**
     * 가챠상세정보 > MY PLACE 목록 조회
     */
    fun selectUserPlaceList(userId: String): List<MyPlaceResponse>

    /**
     * 가챠상세정보 > 조회수 증가
     */
    fun updateViewCount(gachaId: String)

    /**
     * 가챠상세정보 > 즐겨찾기 > GachaWish 저장
     */
    fun insertGachaWish(gachaWish: GachaWish)

    /**
     * 가챠상세정보 > 즐겨찾기 > GachaWish 삭제
     */
    fun deleteGachaWish(userId: String, gachaId: String)
}