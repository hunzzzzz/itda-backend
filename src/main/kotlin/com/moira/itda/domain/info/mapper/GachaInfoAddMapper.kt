package com.moira.itda.domain.info.mapper

import com.moira.itda.global.entity.GachaInfoAdd
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaInfoAddMapper {
    /**
     * 정보등록요청 탭 > 정보등록요청 > 파일 ID 존재 여부 확인
     */
    fun selectFileIdChk(fileId: String): Int

    /**
     * 정보등록요청 탭 > 정보등록요청 > GachaInfoAdd 저장
     */
    fun insertGachaInfoAdd(gachaInfoAdd: GachaInfoAdd)

}