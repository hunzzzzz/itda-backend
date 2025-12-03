package com.moira.itda.domain.login.service;

import com.moira.itda.domain.login.dto.request.LoginRequest;
import com.moira.itda.domain.login.dto.response.LoginResponse;
import com.moira.itda.domain.login.mapper.LoginMapper;
import com.moira.itda.global.auth.CookieHandler;
import com.moira.itda.global.auth.JwtProvider;
import com.moira.itda.global.entity.User;
import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final CookieHandler cookieHandler;
    private final LoginHistoryService loginHistoryService;
    private final LoginMapper loginMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     */
    @Transactional
    public LoginResponse login(
            LoginRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        String ipAddress = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);

        // [1] 이메일로 User 조회
        User user = loginMapper.selectUserByEmail(request.email());

        // [2-1] 이메일 불일치 시, 에러 처리
        if (user == null) {
            throw new ItdaException(ErrorCode.LOGIN_ERROR);
        }
        // [2-2] 비밀번호 불일치 시, 로그인 실패 기록 저장 후 에러 처리
        else if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            loginHistoryService.saveLoginFailureHistory(user.getId(), ipAddress, userAgent);
            throw new ItdaException(ErrorCode.LOGIN_ERROR);
        }
        // [2-3] 그 외의 경우, 로그인 성공 기록 저장
        else {
            loginHistoryService.saveLoginSuccessHistory(user.getId(), ipAddress, userAgent);
        }

        // [3] JWT 토큰 생성
        String atk = jwtProvider.createAtk(user);
        String rtk = jwtProvider.createRtk(user);

        // [4] RefreshToken 저장 (DB, 쿠키)
        loginMapper.updateUserWhenLogin(user.getId(), rtk);
        cookieHandler.putRtkInCookie(rtk, httpServletResponse);

        // [5] AccessToken 리턴
        return new LoginResponse(atk);
    }
}
