package com.moira.itda.domain.user_place.mapper

import com.moira.itda.domain.user_place.dto.response.UserPlaceResponse
import com.moira.itda.global.entity.UserPlace
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserPlaceMapper {
    /**
     * MY PLACE 등록 > name 중복 여부 조회
     */
    fun selectUserPlaceNameChk(userId: String, placeName: String): Boolean

    /**
     * MY PLACE 등록 > 현재 등록된 MY PLACE 개수 조회
     */
    fun selectUserPlaceCnt(userId: String): Long

    /**
     * MY PLACE 등록 > UserPlace 저장
     */
    fun insertUserPlace(userPlace: UserPlace)

    /**
     * MY PLACE 목록 조회
     */
    fun selectUserPlaceList(userId: String): List<UserPlaceResponse>

    /**
     * MY PLACE 기본 설정 > 기존 UserPlace의 default를 N으로 변경
     */
    fun updateUserPlaceDefaultN(userId: String)

    /**
     * MY PLACE 기본 설정 > default를 Y로 변경
     */
    fun updateUserPlaceDefaultY(placeId: String)

    /**
     * MY PLACE 삭제 > UserPlace 정보 조회
     */
    fun selectUserPlaceInfo(placeId: String): HashMap<String, String?>

    /**
     * MY PLACE 삭제
     */
    fun deleteUserPlace(placeId: String)
}