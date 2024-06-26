package com.plans.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

@Component
public class JWTUtils {
    private final static String TOKEN_BEARER_PREFIX = "Bearer";

    @Value("${spring.jwt.access.key}")
    private String accessSignKey;

    @Value("${spring.jwt.refresh.key}")
    private String refreshSignKey;

    @Value("${spring.jwt.access.expiration}")
    private int accessMins;
    
    @Value("${spring.jwt.refresh.expiration}")
    private int refreshMins;

    private long accessExp;
    private long refreshExp;

    @PostConstruct
    public void initExpirationValues() {
        accessExp = TimeUnit.MINUTES.toMillis(accessMins);
        refreshExp = TimeUnit.MINUTES.toMillis(refreshMins);
    }

    private Claims extractAllClaims(String token, String signKey)  {
        SecretKey key = Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
            .setSigningKey(key)   
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public Date extractAccessExpiration(String token) {
        return extractAllClaims(token, accessSignKey).getExpiration();
    }

    public String extractAccessUsername(String token) {
        return extractAllClaims(token, accessSignKey).getSubject();
    }

    public boolean isAccessTokenExpired(String token) {
        return extractAccessExpiration(token).before(new Date());
    }

    public Date extractRefreshExpiration(String token) {
        return extractAllClaims(token, refreshSignKey).getExpiration();
    }

    public String extractRefreshUsername(String token) {
        return extractAllClaims(token, refreshSignKey).getSubject();
    }

    public boolean isRefreshTokenExpired(String token) {
        return extractRefreshExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, UserDetails user, String signKey, long expiration) {
        long issuedAt = System.currentTimeMillis();
        SecretKey key = Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(issuedAt + expiration))
                .signWith(key);

        return builder.compact();
    }

    private String createToken(UserDetails user, String signKey, long expiration) {
        long issuedAt = System.currentTimeMillis();
        SecretKey key = Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));
        Map<String, Object> claims = new HashMap<>();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(issuedAt + expiration))
                .signWith(key);

        return builder.compact();
    }

    public String createAccessToken(Map<String, Object> claims, UserDetails user) {
        return createToken(claims, user, accessSignKey, accessExp);
    }

    public String createRefreshToken(Map<String, Object> claims, UserDetails user) {
        return createToken(claims, user, refreshSignKey, refreshExp);
    }

    public String createAccessToken(UserDetails user) {
        return createToken(user, accessSignKey, accessExp);
    }

    public String createRefreshToken(UserDetails user) {
        return createToken(user, refreshSignKey, refreshExp);
    }

    public boolean validateAccessToken(String token, UserDetails user) {
        String username = extractAccessUsername(token);
        return user.getUsername().equals(username) && !isAccessTokenExpired(token);
    }

    public boolean validateRefreshToken(String token, UserDetails user) {
        String username = extractRefreshUsername(token);
        return user.getUsername().equals(username) && !isRefreshTokenExpired(token);
    }

    public static String getTokenWithoutBearer(String token) throws Exception {
        if (token == null) {
            throw new Exception("Token is not found!");
        }

        return token.substring(TOKEN_BEARER_PREFIX.length());
    }
}
