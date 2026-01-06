package com.moira.itda.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig(
    @Value("\${spring.mail.host}")
    private val host: String,

    @Value("\${spring.mail.port}")
    private val port: Int,

    @Value("\${spring.mail.username}")
    private val username: String,

    @Value("\${spring.mail.password}")
    private val password: String
) {
    @Bean
    fun javaMailSender(): JavaMailSender {
        val sender = JavaMailSenderImpl()

        sender.host = host
        sender.port = port
        sender.username = username
        sender.password = password

        val properties = Properties()
        properties.setProperty("mail.transport.protocol", "smtp")     // SMTP 프로토콜 설정
        properties.setProperty("mail.smtp.auth", "true")              // SMTP 인증 사용 여부
        properties.setProperty("mail.smtp.starttls.enable", "true")   // TLS 암호화 여부
//        properties.setProperty("mail.debug", "true")                // 디버그 사용 여부
        properties.setProperty("mail.smtp.connectiontimeout", "3000") // 타임아웃 (3초)

        sender.javaMailProperties = properties

        return sender
    }
}