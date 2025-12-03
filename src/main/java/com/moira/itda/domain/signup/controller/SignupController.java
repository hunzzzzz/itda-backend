package com.moira.itda.domain.signup.controller;

import com.moira.itda.domain.signup.dto.request.SignupRequest;
import com.moira.itda.domain.signup.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;

    /**
     * 회원가입 > 닉네임 중복 확인
     */
    @GetMapping("/api/signup/check/nickname")
    public ResponseEntity<Object> checkNickname(String nickname) {
        signupService.checkNickname(nickname);

        return ResponseEntity.ok(null);
    }

    /**
     * 회원가입 > 이메일 중복 확인
     */
    @GetMapping("/api/signup/check/email")
    public ResponseEntity<Object> checkEmail(String email) {
        signupService.checkEmail(email);

        return ResponseEntity.ok(null);
    }

    /**
     * 회원가입 > 회원가입
     */
    @PostMapping("/api/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequest request) {
        signupService.signup(request);

        return ResponseEntity.ok(null);
    }
}
