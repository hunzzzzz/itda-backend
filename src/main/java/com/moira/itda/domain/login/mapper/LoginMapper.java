package com.moira.itda.domain.login.mapper;

import com.moira.itda.global.entity.LoginHistory;
import com.moira.itda.global.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    /**
     * 로그인 > 이메일로 User 조회
     */
    User selectUserByEmail(String email);

    /**
     * 로그인 > RTK 저장
     */
    void updateUserWhenLogin(String userId, String rtk);

    /**
     * 로그인 > 로그인 기록 저장
     */
    void insertLoginHistory(LoginHistory loginHistory);

    /**
     * 로그아웃 > RTK 삭제
     */
    void updateUserWhenLogout(String userId);
}
