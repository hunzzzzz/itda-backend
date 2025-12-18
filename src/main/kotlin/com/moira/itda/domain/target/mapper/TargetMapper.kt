package com.moira.itda.domain.target.mapper

import com.moira.itda.domain.target.dto.response.TargetGachaItemResponse
import com.moira.itda.domain.target.dto.response.TargetGachaResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TargetMapper {
    /**
     * 교환 및 판매 대상 지정 모달 > 즐겨찾기 목록 > 가챠 목록 조회 > totalElements 계산
     */
    fun selectGachaWishCnt(userId: String): Long

    /**
     * 교환 및 판매 대상 지정 모달 > 즐겨찾기 목록 > 가챠 목록 조회
     */
    fun selectGachaWishList(userId: String, pageSize: Int, offset: Int): List<TargetGachaResponse>

    /**
     * 교환 및 판매 대상 지정 모달 > 즐겨찾기 목록 > 하위 아이템 목록 조회
     * 교환 및 판매 대상 지정 모달 > 가챠 목록 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<TargetGachaItemResponse>

    /**
     * 교환 및 판매 대상 지정 모달 > 가챠 목록 > totalElements 계산
     */
    fun selectGachaCnt(keywordPattern: String): Long

    /**
     * 교환 및 판매 대상 지정 모달 > 가챠 목록 > 가챠 목록 조회
     */
    fun selectGachaList(keywordPattern: String, pageSize: Int, offset: Int): List<TargetGachaResponse>
}