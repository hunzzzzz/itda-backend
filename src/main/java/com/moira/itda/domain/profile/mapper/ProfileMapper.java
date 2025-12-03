package com.moira.itda.domain.profile.mapper;

import com.moira.itda.domain.profile.dto.response.ProfileResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {
    /**
     * 내 프로필 조회
     */
    ProfileResponse selectUser(String userId);
}
