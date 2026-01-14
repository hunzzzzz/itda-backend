package com.moira.itda.domain.trade_user_report.service

import com.moira.itda.domain.trade_cancel.dto.request.TradeCancelRequest
import com.moira.itda.domain.trade_cancel.service.TradeCancelService
import com.moira.itda.domain.trade_user_report.dto.request.ReportRequest
import com.moira.itda.domain.trade_user_report.mapper.TradeUserReportMapper
import com.moira.itda.global.entity.TradeSuggestStatus
import com.moira.itda.global.entity.TradeUserReport
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeUserReportService(
    private val cancelService: TradeCancelService,
    private val mapper: TradeUserReportMapper
) {
    /**
     * 유저신고
     */
    @Transactional
    fun report(userId: String, chatRoomId: String, request: ReportRequest) {
        // [1] 유효성 검사 (신고사유 입력 여부 확인)
        if (request.reportReason.isBlank()) {
            throw ItdaException(ErrorCode.NO_REPORT_REASON)
        }

        // [2] TradeUserReport 저장
        val tradeUserReport = TradeUserReport.from(userId = userId, chatRoomId = chatRoomId, request = request)
        mapper.insertTradeUserReport(tradeUserReport = tradeUserReport)

        // [3] 거래취소 (status가 APPROVED인 경우에만 거래 취소 서비스 호출)
        val tradeSuggestStatus = mapper.selectTradeSuggestStatus(tradeSuggestId = request.tradeSuggestId) ?: ""
        if (tradeSuggestStatus == TradeSuggestStatus.APPROVED.name) {
            cancelService.cancelTrade(
                userId = userId,
                chatRoomId = chatRoomId,
                request = TradeCancelRequest(
                    userId = userId,
                    tradeId = request.tradeId,
                    tradeSuggestId = request.tradeSuggestId,
                    gachaId = request.gachaId,
                    cancelReason = "[유저 신고에 의한 거래 취소] \n${request.reportReason}"
                )
            )
        }
    }
}