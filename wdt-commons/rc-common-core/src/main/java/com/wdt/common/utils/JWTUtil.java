package com.wdt.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {

    private static final String CLAIM_KEY_USERNAME = "subject";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String SECRET = "wangdongtao";
    private static final long EXPIRATION = 60 * 60L;

    public static String createToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userName);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

    public static String getUsernameFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Failed to get username from token", e);
            return null;
        }
    }

    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    private static String createToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static boolean validateToken(String token, String userName) {
        String username = getUsernameFromToken(token);
        return username != null && username.equals(userName) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private static Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    public static boolean canBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public static String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }
}
