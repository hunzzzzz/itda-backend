package com.moira.itda.domain.sales.mapper

import com.moira.itda.domain.sales.dto.response.SalesItemResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SalesMapper {
    /**
     * 판매등록 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<SalesItemResponse>

    /**
     * 판매등록 > 진행 중인 판매글 존재 여부 확인
     */
    fun selectSalesChk(gachaId: String, userId: String): Int

    /**
     * 판매등록 > 파일 ID 존재 여부 조회
     */
    fun selectImageFileCnt(fileId: String): Int

}