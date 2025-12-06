package com.moira.itda.domain.common.mapper;

import com.moira.itda.domain.common.dto.response.CommonCodeDetailResponse;
import com.moira.itda.domain.common.dto.response.CommonCodeResponse;
import com.moira.itda.global.entity.CommonCode;
import com.moira.itda.global.entity.CommonCodeDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonCodeMapper {
    /**
     * 공통코드 > Key 존재 여부 확인
     */
    int selectCommonCodeKeyChk(String key);

    // ------------------------------------------------------------------------

    /**
     * 공통코드 > 저장 > CommonCode 저장
     */
    void insertCommonCode(CommonCode commonCode);

    /**
     * 공통코드 > 저장 > CommonCodeDetail 저장
     */
    void insertCommonCodeDetail(CommonCodeDetail commonCodeDetail);

    // ------------------------------------------------------------------------

    /**
     * 공통코드 > 단건 조회
     */
    List<CommonCodeDetailResponse> selectCommonCode(String key);

    // ------------------------------------------------------------------------

    /**
     * 공통코드 > 전체 조회
     */
    List<CommonCodeResponse> selectCommonCodeList();

    // ------------------------------------------------------------------------

    /**
     * 공통코드 > 삭제 > CommonCode 삭제
     */
    void deleteCommonCode(String key);

    /**
     * 공통코드 > 삭제 > CommonCodeDetail 삭제
     */
    void deleteCommonCodeDetail(String key);
}
