package com.moira.itda.domain.common.service;

import com.moira.itda.domain.common.dto.CommonCodeResponse;
import com.moira.itda.domain.common.mapper.CommonCodeMapper;
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

    @Transactional(readOnly = true)
    public List<CommonCodeResponse> getCommonCode(String key) {
        // [1] 유효성 검사
        if (commonCodeMapper.selectCommonCodeKeyChk(key) < 1) {
            throw new ItdaException(ErrorCode.COMMON_CODE_NOT_FOUND);
        }
        
        // [2] 조회
        return commonCodeMapper.selectCommonCodeDetailList(key);
    }
}
