package com.moira.itda.domain.signup.mapper;

import com.moira.itda.global.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignupMapper {
    /**
     * 회원가입 > 닉네임 중복 확인
     */
    int selectNicknameCnt(String nickname);

    /**
     * 회원가입 > 이메일 중복 확인
     */
    int selectEmailCnt(String email);

    /**
     * 회원가입 > 회원가입
     */
    void insertUser(User user);
}
