package org.example.bigzuoye.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.bigzuoye.config.JwtConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Resource
    private JwtConfig jwtConfig;

    private SecretKey getSecretKey() {
        // 必须保证 secret 长度 >= 32 字节
        return Keys.hmacShaKeyFor(
                jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * 生成 token（不带 Bearer）
     */
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpire()))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * 统一解析 token
     * ✔ 支持：
     *   - eyJhbGciOi...
     *   - Bearer eyJhbGciOi...
     */
    public Claims parseToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new RuntimeException("Token 为空");
        }

        // 去除 Bearer 前缀（不区分大小写）
        token = token.trim();
        if (token.toLowerCase().startsWith("bearer ")) {
            token = token.substring(7).trim();
        }

        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从 token 中获取 userId
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return Long.valueOf(claims.getSubject());
    }
}

