package com.moira.itda.domain.common.mapper

import com.moira.itda.global.entity.ImageFile
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommonMapper {
    /**
     * 공통 > 이미지 파일 업로드 > ImageFile 저장
     */
    fun insertImageFile(imageFile: ImageFile)
}