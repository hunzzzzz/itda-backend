package com.moira.itda.domain.login.service;

import com.moira.itda.domain.login.mapper.LoginMapper;
import com.moira.itda.global.auth.CookieHandler;
import com.moira.itda.global.auth.SimpleUserAuth;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LogoutService {
    private final CookieHandler cookieHandler;
    private final LoginMapper loginMapper;

    /**
     * 로그아웃
     */
    @Transactional
    public void logout(SimpleUserAuth userAuth, HttpServletResponse httpServletResponse) {
        // RTK 삭제 (DB, 쿠키)
        loginMapper.updateUserWhenLogout(userAuth.userId());
        cookieHandler.removeRtkInCookie(httpServletResponse);
    }
}
