package com.moira.itda.domain.common.service;

import com.moira.itda.domain.common.dto.request.CommonCodeAddRequest;
import com.moira.itda.domain.common.dto.request.CommonCodeDetailAddRequest;
import com.moira.itda.domain.common.dto.response.CommonCodeDetailResponse;
import com.moira.itda.domain.common.dto.response.CommonCodeResponse;
import com.moira.itda.domain.common.mapper.CommonCodeMapper;
import com.moira.itda.global.entity.CommonCode;
import com.moira.itda.global.entity.CommonCodeDetail;
import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommonCodeService {
    private final CommonCodeMapper commonCodeMapper;

    /**
     * 공통코드 > 저장
     */
    @Transactional
    public void add(CommonCodeAddRequest request) {
        // [1] 유효성 검사
        if (commonCodeMapper.selectCommonCodeKeyChk(request.codeKey()) > 0) {
            throw new ItdaException(ErrorCode.ALREADY_EXISTING_COMMON_CODE);
        }

        // [2] CommonCode 저장
        CommonCode commonCode = CommonCode.fromCommonCodeAddRequest(request);
        commonCodeMapper.insertCommonCode(commonCode);

        // [3] CommonCodeDetail 저장
        for (CommonCodeDetailAddRequest detailRequest : request.details()) {
            CommonCodeDetail detail = CommonCodeDetail.fromCommonCodeDetailAddRequest(request.codeKey(), detailRequest);
            commonCodeMapper.insertCommonCodeDetail(detail);
        }
    }

    /**
     * 공통코드 > 단건 조회
     */
    @Transactional(readOnly = true)
    public List<CommonCodeDetailResponse> get(String key) {
        // [1] 유효성 검사
        if (commonCodeMapper.selectCommonCodeKeyChk(key) < 1) {
            throw new ItdaException(ErrorCode.COMMON_CODE_NOT_FOUND);
        }

        // [2] 조회
        return commonCodeMapper.selectCommonCode(key);
    }

    /**
     * 공통코드 > 전체 조회
     */
    @Transactional(readOnly = true)
    public List<CommonCodeResponse> getAll() {
        return commonCodeMapper.selectCommonCodeList();
    }

    /**
     * 공통코드 > 삭제
     */
    @Transactional
    public void delete(String key) {
        // [1] 유효성 검사
        if (commonCodeMapper.selectCommonCodeKeyChk(key) < 1) {
            throw new ItdaException(ErrorCode.COMMON_CODE_NOT_FOUND);
        }

        // [2] CommonCodeDetail -> CommonCode 삭제
        commonCodeMapper.deleteCommonCodeDetail(key);
        commonCodeMapper.deleteCommonCode(key);
    }
}
