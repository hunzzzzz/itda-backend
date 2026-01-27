package com.moira.itda.domain.common.identify.mapper

import com.moira.itda.global.entity.UserIdentifyCode
import com.moira.itda.global.entity.UserIdentifyCodeType
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommonIdentifyMapper {
    /**
     * 본인인증 > 휴대폰번호 존재 여부 조회
     */
    fun selectPhoneNumberChk(phoneNumber: String): Boolean

    /**
     * 본인인증 > UserIdentifyCode 저장
     */
    fun insertUserIdentifyCode(userIdentifyCode: UserIdentifyCode)

    /**
     * 본인인증 > 인증코드 확인 > UserIdentifyCode 조회
     */
    fun selectUserIdentifyCode(phoneNumber: String, type: UserIdentifyCodeType): UserIdentifyCode?

    /**
     * 본인인증 > 인증코드 확인 > 기존 User의 name, ci 조회
     */
    fun selectUserInfo(phoneNumber: String): HashMap<String, String?>

    /**
     * 본인인증 > 인증코드 확인 > UserIdentifyCode 수정
     */
    fun updateUserIdentifyCode(phoneNumber: String, name: String, ci: String, type: UserIdentifyCodeType)

    /**
     * 스케쥴러 (30분 간격) > UserIdentifyCode 삭제
     */
    fun deleteUserIdentifyCode()
}