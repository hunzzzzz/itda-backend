package com.moira.itda.domain.signup.service;

import com.moira.itda.domain.signup.dto.request.SignupRequest;
import com.moira.itda.domain.signup.mapper.SignupMapper;
import com.moira.itda.global.entity.User;
import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignupService {
    private final PasswordEncoder passwordEncoder;
    private final SignupMapper signupMapper;

    /**
     * 회원가입 > 닉네임 중복 확인
     */
    @Transactional(readOnly = true)
    public void checkNickname(String nickname) {
        if (signupMapper.selectNicknameCnt(nickname) > 0) {
            throw new ItdaException(ErrorCode.ALREADY_USING_NICKNAME);
        }
    }

    /**
     * 회원가입 > 이메일 중복 확인
     */
    @Transactional(readOnly = true)
    public void checkEmail(String email) {
        if (signupMapper.selectEmailCnt(email) > 0) {
            throw new ItdaException(ErrorCode.ALREADY_USING_EMAIL);
        }
    }

    /**
     * 회원가입 > 회원가입
     */
    @Transactional
    public void signup(SignupRequest request) {
        // [1] 유효성 검사
        this.validate(request);

        // [2] User 객체 획득
        User user = User.fromSignupRequest(request, passwordEncoder);

        // [3] User 저장 (DB)
        signupMapper.insertUser(user);
    }

    /**
     * 회원가입 > 회원가입 > 유효성 검사
     */
    private void validate(SignupRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ItdaException(ErrorCode.INVALID_NAME);
        }
        if (request.nickname() == null || request.nickname().isBlank()) {
            throw new ItdaException(ErrorCode.INVALID_NICKNAME);
        }
        if (request.phone() == null || request.phone().isBlank()) {
            throw new ItdaException(ErrorCode.INVALID_PHONE);
        }
        if (request.email() == null || request.email().isBlank()) {
            throw new ItdaException(ErrorCode.INVALID_EMAIL);
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new ItdaException(ErrorCode.INVALID_PASSWORD);
        }
        if (signupMapper.selectNicknameCnt(request.nickname()) > 0) {
            throw new ItdaException(ErrorCode.ALREADY_USING_NICKNAME);
        }
        if (signupMapper.selectEmailCnt(request.email()) > 0) {
            throw new ItdaException(ErrorCode.ALREADY_USING_EMAIL);
        }
    }
}
