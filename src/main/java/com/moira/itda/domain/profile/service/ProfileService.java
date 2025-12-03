package com.moira.itda.domain.profile.service;

import com.moira.itda.domain.profile.dto.response.ProfileResponse;
import com.moira.itda.domain.profile.mapper.ProfileMapper;
import com.moira.itda.global.auth.SimpleUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileMapper profileMapper;

    /**
     * 내 프로필 조회
     */
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(SimpleUserAuth userAuth) {
        String userId = userAuth.userId();

        return profileMapper.selectUser(userId);
    }
}
