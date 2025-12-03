package com.moira.itda.domain.file.mapper;

import com.moira.itda.global.entity.ImageFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageFileMapper {
    /**
     * 이미지 파일 저장
     */
    void insertImageFile(ImageFile imageFile);
}
