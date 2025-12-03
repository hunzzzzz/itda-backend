package com.moira.itda.domain.profile.controller;

import com.moira.itda.domain.profile.dto.response.ProfileResponse;
import com.moira.itda.domain.profile.service.ProfileService;
import com.moira.itda.global.aop.UserPrincipal;
import com.moira.itda.global.auth.SimpleUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final ProfileService profileService;

    /**
     * 내 프로필 조회 (simple)
     */
    @GetMapping("/api/me/simple")
    public ResponseEntity<SimpleUserAuth> getSimpleProfile(@UserPrincipal SimpleUserAuth userAuth) {
        return ResponseEntity.ok(userAuth);
    }

    /**
     * 내 프로필 조회
     */
    @GetMapping("/api/me")
    public ResponseEntity<ProfileResponse> getProfile(@UserPrincipal SimpleUserAuth userAuth) {
        ProfileResponse profileResponse = profileService.getProfile(userAuth);

        return ResponseEntity.ok(profileResponse);
    }
}
