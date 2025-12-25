package com.moira.itda.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    /**
     * WebSocket 연결 엔드포인트 설정
     */
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws-itda") // 엔드포인트
            .setAllowedOriginPatterns("*")        // CORS 설정
            .withSockJS()                         // 낮은 버전의 브라우저 호환성
    }

    /**
     * 메시지 브로커 설정 (pub/sub)
     */
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // /sub으로 시작하는 경로는 메시지 브로커가 가로채서 구독자들에게 전달 (구독)
        registry.enableSimpleBroker("/sub")

        // /pub으로 시작하는 경로는 @MessageMapping이 붙은 컨트롤러로 향함 (발행)
        registry.setApplicationDestinationPrefixes("/pub")
    }
}