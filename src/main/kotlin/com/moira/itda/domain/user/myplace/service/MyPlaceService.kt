package com.moira.itda.domain.user.myplace.service

import com.moira.itda.domain.user.myplace.dto.request.MyPlaceRequest
import com.moira.itda.domain.user.myplace.dto.response.MyPlaceResponse
import com.moira.itda.domain.user.myplace.mapper.MyPlaceMapper
import com.moira.itda.global.entity.UserPlace
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MyPlaceService(
    private val mapper: MyPlaceMapper
) {
    companion object {
        const val USER_PLACE_MAX_COUNT = 5
    }

    /**
     * MY PLACE 등록
     */
    @Transactional
    fun add(userId: String, request: MyPlaceRequest) {
        // [1] name 중복 여부 검증
        if (mapper.selectUserPlaceNameChk(userId = userId, placeName = request.name)) {
            throw ItdaException(ErrorCode.USING_PLACE_NAME)
        }

        // [2] 유저의 MY PLACE 개수 조회
        val count = mapper.selectUserPlaceCnt(userId = userId)
        if (count >= USER_PLACE_MAX_COUNT) {
            throw ItdaException(ErrorCode.PLACE_MAXIMUM_FIVE)
        }

        // [3] UserPlace 저장
        val default = if (count < 1) "Y" else "N"
        val userPlace = UserPlace.from(userId = userId, request = request, isDefault = default)

        mapper.insertUserPlace(userPlace = userPlace)
    }

    /**
     * MY PLACE 목록 조회
     */
    @Transactional(readOnly = true)
    fun getList(userId: String): List<MyPlaceResponse> {
        return mapper.selectUserPlaceList(userId = userId)
    }

    /**
     * MY PLACE 기본 설정
     */
    @Transactional
    fun setDefault(userId: String, placeId: String) {
        // [1] UserPlace 정보 조회
        val infoMap = mapper.selectUserPlaceInfo(placeId = placeId)

        // [2] 권한 검증
        if (userId != (infoMap["user_id"] ?: "")) {
            throw ItdaException(ErrorCode.OTHERS_PLACE)
        }

        // [3] 기존 default = "Y"인 UserPlace의 default를 "N"으로 수정
        mapper.updateUserPlaceDefaultN(userId = userId)

        // [4] 해당 UserPlace의 default를 "Y"로 수정
        mapper.updateUserPlaceDefaultY(placeId = placeId)
    }

    /**
     * MY PLACE 삭제
     */
    @Transactional
    fun delete(userId: String, placeId: String) {
        // [1] UserPlace 정보 조회
        val infoMap = mapper.selectUserPlaceInfo(placeId = placeId)

        // [2] 권한 검증
        if (userId != (infoMap["user_id"] ?: "")) {
            throw ItdaException(ErrorCode.OTHERS_PLACE)
        }

        // [3] default 여부 검증
        if ((infoMap["is_default"] ?: "") == "Y") {
            throw ItdaException(ErrorCode.CANNOT_DELETE_DEFAULT_PLACE)
        }

        // [4] UserPlace 삭제
        mapper.deleteUserPlace(placeId = placeId)
    }
}