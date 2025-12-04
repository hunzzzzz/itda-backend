package com.moira.itda.domain.common.mapper;

import com.moira.itda.domain.common.dto.CommonCodeResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonCodeMapper {
    /**
     * 공통 코드 존재 여부 확인
     */
    int selectCommonCodeKeyChk(String key);

    /**
     * 공통 코드 조회
     */
    List<CommonCodeResponse> selectCommonCodeDetailList(String key);
}
