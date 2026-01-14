package com.moira.itda.global.mail.component

import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserMailSender(
    private val sender: JavaMailSender
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 이메일 전송
     */
    @Async
    fun send(email: String, subject: String, text: String): Result<Unit> {
        return runCatching {
            log.info("[이메일 전송] 이메일 전송 시도중...")

            val mm = sender.createMimeMessage()
            val helper = MimeMessageHelper(mm, false, "UTF-8")

            helper.setTo(email)
            helper.setSubject(subject)
            helper.setText(text)

            sender.send(mm)

            log.info("[이메일 전송] 이메일 전송 완료!")
        }
    }
}