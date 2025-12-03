package com.moira.itda.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//    private final ExceptionHandlerFilter exceptionHandlerFilter;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF, FormLogin, Basic 인증 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                // 2. 세션 관리: STATELESS (JWT 기반)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 3. 인가 규칙 설정
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/signup/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/signup/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                                .anyRequest().authenticated()
                );
                // 4. 예외 처리 핸들러 등록
//                .exceptionHandling(customizer -> customizer.accessDeniedHandler(customAccessDeniedHandler))
                // 5. 필터 추가
//                .addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
