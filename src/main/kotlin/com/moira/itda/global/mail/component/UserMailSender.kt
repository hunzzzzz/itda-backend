package com.moira.itda.global.mail.component

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserMailSender(
    private val sender: JavaMailSender
) {
    @Async
    fun send(email: String, subject: String, text: String): Result<Unit> {
        return runCatching {
            val mm = sender.createMimeMessage()
            val helper = MimeMessageHelper(mm, false, "UTF-8")

            helper.setTo(email)
            helper.setSubject(subject)
            helper.setText(text)

            sender.send(mm)
        }
    }
}