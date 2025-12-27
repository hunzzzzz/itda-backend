package com.moira.itda.domain.common.mapper

import com.moira.itda.domain.common.dto.response.CodeDetailResponse
import com.moira.itda.domain.common.dto.response.ImageFileUrlResponse
import com.moira.itda.global.entity.ImageFile
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommonMapper {
    /**
     * 공통 > 이미지 파일 업로드 > ImageFile 저장
     */
    fun insertImageFile(imageFile: ImageFile)

    /**
     * 공통 > 이미지 목록 조회
     */
    fun selectImageFileUrl(fileId: String): List<ImageFileUrlResponse>

    /**
     * 공통 > 파일 ID 존재 여부 확인
     */
    fun selectFileIdChk(fileId: String): Int

    /**
     * 공통 > 공통코드 조회 > 세부코드 목록 조회
     */
    fun selectCodeDetailList(key: String): List<CodeDetailResponse>
}