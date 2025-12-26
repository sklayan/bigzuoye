package org.example.bigzuoye.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.bigzuoye.config.JwtConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class JwtUtil {

    @Resource
    private JwtConfig jwtConfig;

    /**
     * 生成 token
     */
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpire()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }

    /**
     * 解析 token
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
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
