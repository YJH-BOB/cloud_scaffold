package com.wdt.common.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {

    // 创建对象主体
    private static final String CLAIM_KEY_USERNAME = "subject";
    // 创建创建时间
    private static final String CLAIM_KEY_CREATED = "created";

    // 创建加密盐
    private static final String SECRET = "wangdongtao";
    // 过期时间一小时
    private static final Long EXPIRATION = 60 * 60L;

    /**
     * 根据用户名生成token
     *
     * @param userName 用户名
     * @return token字符串
     */
    public static String createToken(String userName) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userName);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

    /**
     * 根据token获取用户名
     *
     * @param token token字符串
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        String username = "";
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
            log.info("error:{}", "Failed to get username from token");
        }
        return username;
    }

    /**
     * 从token中获取荷载
     *
     * @param token token字符串
     * @return 荷载
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据负载生成jwt token
     *
     * @param claims 负载
     * @return token字符串
     */
    private static String createToken(Map<String, Object> claims) {
        // jjwt构建jwt builder
        // 设置信息，过期时间，signnature
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 生成token失效时间
     *
     * @return 失效时间
     */
    private static Date expirationDate() {
        //失效时间为：系统当前毫秒数+我们设置的时间（s）*1000=》毫秒
        return new Date(System.currentTimeMillis() + EXPIRATION * 1000);
    }

    /**
     * 判断token是否有效
     *
     * @param token    token字符串
     * @param userName 用户名
     * @return 是否有效
     */
    public static boolean validateToken(String token, String userName) {
        //判断token是否过期
        //判断token是否和userDetails中的一致
        //我们要做的 是先获取用户名
        String username = getUsernameFromToken(token);
        return username.equals(userName) && !isTokenExpired(token);
    }

    /**
     * 判断token、是否失效
     *
     * @param token token字符串
     * @return 是否失效
     */
    private static boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFeomToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从荷载中获取时间
     *
     * @param token token字符串
     * @return 时间
     */
    private static Date getExpiredDateFeomToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token是否可以被刷新
     *
     * @param token token字符串
     * @return 是否可刷新
     */
    public static boolean canBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     *
     * @param token token字符串
     * @return 刷新后的token字符串
     */
    public static String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        // 修改为当前时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

    /**
     * 获取token签发时间
     */
}
