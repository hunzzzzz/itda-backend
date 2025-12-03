package com.moira.itda.global.auth.filter;

import com.moira.itda.global.auth.JwtProvider;
import com.moira.itda.global.auth.SimpleUserAuth;
import com.moira.itda.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final FilterErrorSender filterErrorSender;
    private final JwtProvider jwtProvider;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final List<RequestMatcher> EXCLUDE_REQUEST_MATCHERS = List.of(
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/api/signup/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/signup/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/login/**")
    );

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return EXCLUDE_REQUEST_MATCHERS.stream().anyMatch(matcher -> matcher.matches(request));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        log.info("[요청 URI] {} {}", request.getMethod(), request.getRequestURI());

        // [1] Authorization 헤더 추출
        String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeaderValue == null || !authorizationHeaderValue.startsWith("Bearer ")) {
            filterErrorSender.sendErrorResponse(response, ErrorCode.INVALID_AUTHORIZATION_HEADER);
            return;
        }

        // [2] AccessToken 추출
        String accessToken = jwtProvider.substringToken(authorizationHeaderValue);

        try {
            // [3] 토큰 검증
            Claims claims = jwtProvider.validate(accessToken);

            // [4] 유저 정보 추출
            String userId = claims.getSubject();
            String email = claims.get("email", String.class);
            String nickname = claims.get("nickname", String.class);
            String role = claims.get("role", String.class);
            SimpleUserAuth simpleUserAuth = new SimpleUserAuth(userId, email, nickname, role);

            // [5] Spring Security 권한 및 Authentication 객체 생성
            List<SimpleGrantedAuthority> authorities = Stream.of("ROLE_" + role).map(SimpleGrantedAuthority::new).toList();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(simpleUserAuth, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            ErrorCode errorCode;

            if (e instanceof ExpiredJwtException) {
                errorCode = ErrorCode.EXPIRED_ATK;
            } else if (e instanceof SignatureException) {
                errorCode = ErrorCode.INVALID_SIGNATURE;
            } else if (e instanceof UnsupportedJwtException || e instanceof MalformedJwtException) {
                errorCode = ErrorCode.INVALID_TOKEN;
            } else {
                log.error("[ItdaApplication] 에러 발생! {}", e.getMessage());
                errorCode = ErrorCode.INTERNAL_SYSTEM_ERROR;
            }

            filterErrorSender.sendErrorResponse(response, errorCode);
        }
    }
}
