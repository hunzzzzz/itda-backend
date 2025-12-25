package com.moira.itda.domain.user.mytradesuggest.service

import com.moira.itda.domain.user.mytradesuggest.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.user.mytradesuggest.dto.response.SuggestPageResponse
import com.moira.itda.domain.user.mytradesuggest.mapper.MyTradeSuggestMapper
import com.moira.itda.global.entity.ChatRoom
import com.moira.itda.global.entity.TradeType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_SUGGEST_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MyTradeSuggestService(
    private val myTradeSuggestMapper: MyTradeSuggestMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
    companion object {
        const val FIRST_MESSAGE = "거래 제안이 승인되었습니다. 대화를 시작해 보세요!"
    }

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회
     */
    @Transactional(readOnly = true)
    fun getMyTradeSuggests(tradeId: String, page: Int): SuggestPageResponse {
        // [1] 거래 type 조회
        val type = myTradeSuggestMapper.selectTradeType(tradeId = tradeId)
            ?: throw ItdaException(ErrorCode.INVALID_SUGGEST_TYPE)

        // [2] 변수 세팅
        val pageSize = MY_TRADE_SUGGEST_LIST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [3] 거래 제안 정보 조회
        val totalElements = when (type) {
            TradeType.SALES.name -> {
                myTradeSuggestMapper.selectTradePurchaseSuggestListCnt(tradeId = tradeId)
            }

            TradeType.EXCHANGE.name -> {
                myTradeSuggestMapper.selectTradeExchangeSuggestListCnt(tradeId = tradeId)
            }

            else -> throw ItdaException(ErrorCode.INVALID_SUGGEST_TYPE)
        }
        val contents: List<*> = when (type) {
            TradeType.SALES.name -> {
                myTradeSuggestMapper.selectTradePurchaseSuggestList(
                    tradeId = tradeId, pageSize = pageSize, offset = offset
                )
            }

            TradeType.EXCHANGE.name -> {
                myTradeSuggestMapper.selectTradeExchangeSuggestList(
                    tradeId = tradeId, pageSize = pageSize, offset = offset
                )
            }

            else -> throw ItdaException(ErrorCode.INVALID_SUGGEST_TYPE)
        }

        // [4] 오프셋 페이지네이션 적용
        val pageResponse = offsetPaginationHandler.getPageResponse(
            pageSize = pageSize,
            page = page,
            totalElements = totalElements
        )

        // [5] DTO 병합 후 리턴
        return SuggestPageResponse(content = contents, page = pageResponse)
    }

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인
     */
    @Transactional
    fun approve(userId: String, tradeId: String, suggestId: Long): ChatRoomIdResponse {
        // [1] 거래 type 조회
        val type = myTradeSuggestMapper.selectTradeType(tradeId = tradeId)
            ?: throw ItdaException(ErrorCode.INVALID_SUGGEST_TYPE)

        // [2] 상태값 변경 (APPROVED)
        when (type) {
            TradeType.SALES.name -> {
                myTradeSuggestMapper.updateTradePurchaseSuggestStatusApproved(tradeId = tradeId, suggestId = suggestId)
            }

            TradeType.EXCHANGE.name -> {
                myTradeSuggestMapper.updateTradeExchangeSuggestStatusApproved(tradeId = tradeId, suggestId = suggestId)
            }

            else -> throw ItdaException(ErrorCode.INVALID_SUGGEST_TYPE)
        }

        // [3] 채팅방 생성 (ChatRoom 저장)
        val buyerId = when (type) {
            TradeType.SALES.name -> {
                myTradeSuggestMapper.selectTradePurchaseSuggestUserId(tradeId = tradeId, suggestId = suggestId)
            }

            TradeType.EXCHANGE.name -> {
                myTradeSuggestMapper.selectTradeExchangeSuggestUserId(tradeId = tradeId, suggestId = suggestId)
            }

            else -> throw ItdaException(ErrorCode.INVALID_SUGGEST_TYPE)
        } ?: throw ItdaException(ErrorCode.USER_NOT_FOUND)

        val chatRoom = ChatRoom.from(tradeId = tradeId, sellerId = userId, buyerId = buyerId)
        myTradeSuggestMapper.insertChatRoom(chatRoom = chatRoom)

        return ChatRoomIdResponse(chatRoomId = chatRoom.id)
    }

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 거절
     */
    @Transactional
    fun reject(tradeId: String, suggestId: Long) {
        // [1] 거래 type 조회
        val type = myTradeSuggestMapper.selectTradeType(tradeId = tradeId)
            ?: throw ItdaException(ErrorCode.INVALID_SUGGEST_TYPE)

        // [2] 상태값 변경 (REJECTED)
        when (type) {
            TradeType.SALES.name -> {
                myTradeSuggestMapper.updateTradePurchaseSuggestStatusRejected(tradeId = tradeId, suggestId = suggestId)
            }

            TradeType.EXCHANGE.name -> {
                myTradeSuggestMapper.updateTradeExchangeSuggestStatusRejected(tradeId = tradeId, suggestId = suggestId)
            }

            else -> throw ItdaException(ErrorCode.INVALID_SUGGEST_TYPE)
        }
    }
}