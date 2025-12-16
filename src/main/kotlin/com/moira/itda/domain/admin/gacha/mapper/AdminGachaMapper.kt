package com.moira.itda.domain.admin.gacha.mapper

import com.moira.itda.global.entity.Gacha
import com.moira.itda.global.entity.GachaItem
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AdminGachaMapper {
    /**
     * 어드민 페이지 > 가챠정보 > 등록 > 등록 파일 개수 확인
     */
    fun selectImageFileChk(fileId: String): Int

    /**
     * 어드민 페이지 > 가챠정보 > 등록 > Gacha 저장
     */
    fun insertGacha(gacha: Gacha)

    /**
     * 어드민 페이지 > 가챠정보 > 등록 > GachaItem 저장
     */
    fun insertGachaItem(gachaItem: GachaItem)
}