package com.moira.itda.global.auth.aop

import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserPrincipalArgumentResolver : HandlerMethodArgumentResolver {
    /**
     * 해당 파라미터가 @UserPrincipal 어노테이션을 가지고, 타입이 UserAuth인지 확인
     */
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(UserPrincipal::class.java)
                && parameter.getParameterType() == UserAuth::class.java
    }

    /**
     * SecurityContext의 현재 인증된 사용자의 Principal 객체(SimpleUserAuth) 획득
     */
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val authentication = SecurityContextHolder.getContext().authentication

        return authentication.principal
    }
}
