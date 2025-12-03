package com.moira.itda.domain.login.controller;

import com.moira.itda.domain.login.service.LogoutService;
import com.moira.itda.global.aop.UserPrincipal;
import com.moira.itda.global.auth.SimpleUserAuth;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LogoutController {
    private final LogoutService logoutService;

    /**
     * 로그아웃
     */
    @PostMapping("/api/logout")
    public ResponseEntity<Object> logout(
            @UserPrincipal SimpleUserAuth userAuth,
            HttpServletResponse httpServletResponse
    ) {
        logoutService.logout(userAuth, httpServletResponse);

        return ResponseEntity.ok(null);
    }
}
