package com.moira.itda.global.config

import com.moira.itda.global.auth.filter.CustomAccessDeniedHandler
import com.moira.itda.global.auth.filter.ExceptionHandlerFilter
import com.moira.itda.global.auth.filter.JwtAuthenticationFilter
import com.moira.itda.global.auth.filter.LoggingFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val loggingFilter: LoggingFilter
) {
    /**
     * PasswordEncoder Bean 등록
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    /**
     * AuthenticationManager Bean 등록
     */
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    /**
     * 스프링 시큐리티 필터 체인 정의
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            // [1] CSRF, FormLogin, Basic 인증 비활성화
            .csrf { it.disable() }
            .cors { }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            // [2] 세션 관리: STATELESS (JWT 기반)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            // [3] 인가 규칙 설정
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/health/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/ws-itda/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/signup/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/signup/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/identify/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/reset/password/**").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/reset/password/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/code/**").permitAll()
                    .anyRequest().authenticated()
            }
            // [4] 예외 처리 핸들러 등록
            .exceptionHandling { it.accessDeniedHandler(customAccessDeniedHandler) }
            // [5] 커스텀 필터 추가
            .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}