package com.moira.itda.domain.gacha.mapper

import com.moira.itda.domain.gacha.dto.response.*
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaMapper {
    /**
     * 교환/판매 대상 지정 모달 > 즐겨찾기 목록 > 가챠목록 조회 > totalElements 계산
     */
    fun selectGachaListCnt(keyword: String): Long

    // --------------------------------------------------------------------------------------------------- //

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

    // --------------------------------------------------------------------------------------------------- //

    /**
     * 교환/판매 대상 지정 모달 > 가챠목록 > 가챠목록 조회
     */
    fun selectTargetGachaList(keyword: String, pageSize: Int, offset: Int): List<TargetGachaResponse>

    /**
     * 교환/판매 대상 지정 모달 > 가챠목록 > 하위 아이템 목록 조회
     * 교환/판매 대상 지정 모달 > 즐겨찾기 가챠목록 > 하위 아이템 목록 조회
     */
    fun selectTargetGachaItemList(gachaId: String): List<TargetGachaItemResponse>

    /**
     * 교환/판매 대상 지정 모달 > 즐겨찾기 가챠목록 > 가챠목록 조회 > totalElements 계산
     * 마이페이지 > 즐겨찾기 > 즐겨찾기 가챠목록 > totalElements 계산
     */
    fun selectWishGachaListCnt(userId: String): Long

    /**
     * 교환/판매 대상 지정 모달 > 즐겨찾기 가챠목록 > 가챠목록 조회
     */
    fun selectTargetWishGachaList(userId: String, pageSize: Int, offset: Int): List<TargetGachaResponse>

    // --------------------------------------------------------------------------------------------------- //

    /**
     * 마이페이지 > 즐겨찾기 > 즐겨찾기 가챠목록
     */
    fun selectWishGachaList(userId: String, pageSize: Int, offset: Int): List<GachaResponse>

    // --------------------------------------------------------------------------------------------------- //

    /**
     * 거래제안 모달 > 가챠 하위 아이템 목록 조회
     * 교환등록 > 가챠 하위 아이템 목록 조회
     * 판매등록 > 가챠 하위 아이템 목록 조회
     */
    fun selectGachaItemNameList(gachaId: String): List<GachaItemNameResponse>
}