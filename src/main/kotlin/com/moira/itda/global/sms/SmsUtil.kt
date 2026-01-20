package com.moira.itda.global.sms

import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import net.nurigo.sdk.NurigoApp
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.service.DefaultMessageService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class SmsUtil(
    @Value("\${coolsms.apikey}")
    private val apiKey: String,

    @Value("\${coolsms.apisecret}")
    private val apiSecret: String,

    @Value("\${coolsms.fromnumber}")
    private val fromNumber: String,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val messageService: DefaultMessageService = NurigoApp.initialize(
        apiKey = apiKey,
        apiSecretKey = apiSecret,
        domain = "https://api.coolsms.co.kr"
    )

    /**
     * SMS 문자 전송
     */
    @Async
    fun sendSms(toNumber: String, message: String) {
        runCatching {
            val message = Message().apply {
                from = fromNumber
                to = toNumber
                text = message
            }

            this.messageService.sendOne(SingleMessageSendingRequest(message))

            logger.info("[SmsUtil] 휴대폰 SMS 전송이 완료되었습니다. to: $toNumber")
        }.onFailure {
            logger.error("[SmsUtil] 휴대폰 SMS 전송 과정에서 에러가 발생했습니다. {}", it.message)
            throw ItdaException(ErrorCode.SMS_SYSTEM_ERROR)
        }
    }
}