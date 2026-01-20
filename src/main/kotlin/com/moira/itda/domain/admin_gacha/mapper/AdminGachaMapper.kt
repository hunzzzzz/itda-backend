package com.moira.itda.domain.admin_gacha.mapper

import com.moira.itda.domain.admin_gacha.dto.response.AdminGachaItemResponse
import com.moira.itda.domain.admin_gacha.dto.response.AdminGachaResponse
import com.moira.itda.global.entity.Gacha
import com.moira.itda.global.entity.GachaItem
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AdminGachaMapper {
    /**
     * 가챠 등록 > 파일 개수 조회
     */
    fun selectImageFileCnt(fileId: String): Int

    /**
     * 가챠 등록 > Gacha 저장
     */
    fun insertGacha(gacha: Gacha)

    /**
     * 가챠 등록 > GachaItem 저장
     */
    fun insertGachaItem(gachaItem: GachaItem)

    /**
     * 가챠 목록 조회
     */
    fun selectGachaList(keyword: String): List<AdminGachaResponse>

    /**
     * 가챠아이템 목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<AdminGachaItemResponse>

    /**
     * 가챠 수정 > Gacha 수정
     */
    fun updateGacha(gachaId: String, newTitle: String, newManufacturer: String)

    /**
     * 가챠아이템 수정 > GachaItem 수정
     */
    fun updateGachaItem(gachaItemId: Long, newName: String)

    /**
     * 가챠아이템 삭제 > GachaItem 삭제
     */
    fun deleteGachaItem(gachaItemId: Long)
}