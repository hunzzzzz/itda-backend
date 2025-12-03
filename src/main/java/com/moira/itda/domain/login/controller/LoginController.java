package com.moira.itda.domain.login.controller;

import com.moira.itda.domain.login.dto.request.LoginRequest;
import com.moira.itda.domain.login.dto.response.LoginResponse;
import com.moira.itda.domain.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    /**
     * 로그인
     */
    @PostMapping("/api/login")
    public ResponseEntity<Object> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        LoginResponse loginResponse = loginService.login(request, httpServletRequest, httpServletResponse);

        return ResponseEntity.ok(loginResponse);
    }
}
