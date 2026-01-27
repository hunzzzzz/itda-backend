package com.moira.itda.domain.common.image.mapper

import com.moira.itda.domain.common.image.dto.response.FileUrlResponse
import com.moira.itda.global.entity.ImageFile
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommonImageMapper {
    /**
     * 이미지 업로드 > ImageFile 저장
     */
    fun insertImageFile(imageFile: ImageFile)

    /**
     * 이미지 목록 조회
     */
    fun selectImageFileUrl(fileId: String): List<FileUrlResponse>

    /**
     * 파일 ID 존재 여부 확인
     */
    fun selectFileIdChk(fileId: String): Boolean

    /**
     * ImageFile 삭제
     */
    fun deleteImageFile(fileId: String)
}