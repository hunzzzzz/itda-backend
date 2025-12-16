package com.moira.itda.global.config

import com.moira.itda.global.auth.aop.UserPrincipalArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val userPrincipalArgumentResolver: UserPrincipalArgumentResolver
) : WebMvcConfigurer {
    /**
     * 모든 엔드포인트에 대한 CORS 설정
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "https://itda.lovable.app")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowCredentials(true)
            .allowedHeaders("*")
            .maxAge(3600)
    }

    /**
     * ArgumentResolver 등록
     */
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(userPrincipalArgumentResolver)
    }
}