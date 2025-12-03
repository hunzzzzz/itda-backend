package com.moira.itda.global.auth;

import com.moira.itda.global.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.secret.key}")
    private String secretKey;

    private static final String JWT_ISSUER = "ITDA_LOCAL";                    // 토큰 발급자
    private static final Long JWT_EXPIRATION_TIME_ATK = 1000 * 60 * 60L;      // 1시간
    private static final Long JWT_EXPIRATION_TIME_RTK = 1000 * 60 * 60 * 24L; // 24시간
    private SecretKey key;

    @PostConstruct
    void init() {
        this.key = Keys.hmacShaKeyFor(
                Base64.getDecoder().decode(secretKey)
        );
    }

    public String createAtk(User user) {
        return this.createToken(user, JWT_EXPIRATION_TIME_ATK);
    }

    public String createRtk(User user) {
        return this.createToken(user, JWT_EXPIRATION_TIME_RTK);
    }

    private String createToken(User user, Long expirationTime) {
        Date now = new Date();
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("email", user.getEmail());
        claims.put("nickname", user.getNickname());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .subject(user.getId())
                .claims(claims)
                .expiration(new Date(now.getTime() + expirationTime))
                .issuedAt(now)
                .issuer(JWT_ISSUER)
                .signWith(key)
                .compact();
    }

    public String substringToken(String value) {
        return value.substring("Bearer ".length());
    }

    public Claims validate(String atk) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(atk).getPayload();
    }
}
