package com.moira.itda.domain.user.support.service

import com.moira.itda.domain.common.image.mapper.CommonImageMapper
import com.moira.itda.domain.user.support.dto.request.SupportRequest
import com.moira.itda.domain.user.support.dto.response.SupportPageResponse
import com.moira.itda.domain.user.support.mapper.UserSupportMapper
import com.moira.itda.global.entity.UserSupport
import com.moira.itda.global.entity.UserSupportType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_SUPPORT_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSupportService(
    private val commonImageMapper: CommonImageMapper,
    private val mapper: UserSupportMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 문의 등록 > 유효성 검사
     */
    private fun validate(request: SupportRequest) {
        // 타입
        runCatching { UserSupportType.valueOf(request.type) }
            .onFailure { throw ItdaException(ErrorCode.INVALID_USER_SUPPORT_TYPE) }

        // 파일
        if (!request.fileId.isNullOrBlank()) {
            if (!commonImageMapper.selectFileIdChk(fileId = request.fileId)) {
                throw ItdaException(ErrorCode.FILE_NOT_FOUND)
            }
        }
    }

    /**
     * 문의 등록
     */
    @Transactional
    fun addUserSupport(userId: String, request: SupportRequest) {
        // [1] 유효성 검사
        this.validate(request = request)

        // [2] 저장
        val userSupport = UserSupport.from(userId = userId, request = request)
        mapper.insertUserSupport(userSupport = userSupport)
    }

    /**
     * 문의 목록 조회
     */
    @Transactional(readOnly = true)
    fun getSupportList(userId: String, page: Int): SupportPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_SUPPORT_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 문의 목록 조회
        val totalElements = mapper.selectUserSupportListCnt(userId = userId)
        val contents = mapper.selectUserSupportList(userId = userId, pageSize = pageSize, offset = offset)

        // [3] 이미지 파일 목록 조회
        contents.forEach {
            if (it.fileId != null) {
                it.fileUrls = commonImageMapper.selectImageFileUrl(fileId = it.fileId).map { it.fileUrl }
            }
        }

        // [4] 오프셋 페이지네이션 적용
        val page = pageHandler.getPageResponse(pageSize = pageSize, page = page, totalElements = totalElements)

        // [5] DTO 병합 후 리턴
        return SupportPageResponse(content = contents, page = page)
    }
}