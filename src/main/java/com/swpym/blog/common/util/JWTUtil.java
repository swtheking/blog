package com.swpym.blog.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @description: jwt认证工具类
 * @author: shaowei
 * @date: 2020-03-09 13:44
 */
public class JWTUtil {

    // 日志
    private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    // 过期时间30分钟
    private static final long EXPIRE_TIME = 30 * 60 * 1000;

    /*
     * @description: 生成签名,30min后过期
     * @author: shaowei
     * @date: 2020-03-09 13:47:57
     * @param username    用户名
     * @param secret      用户密码
     * @return: java.lang.String  加密的token
     */
    public static String sign(String username, String secret) {
        // 设置过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        // 密码加密
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /*
     * @description:  校验token是否正确
     * @author: shaowei
     * @date: 2020-03-09 13:50:03
     * @param token     密钥
     * @param username  用户名
     * @param secret    用户密码
     * @return: boolean  是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", username)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    /*
     * @description: 获得token中的用户信息无需secret解密也能获得
     * @author: shaowei
     * @date: 2020-03-09 13:51:32
     * @param token    密钥
     * @return: java.lang.String
     */
    public static String getUsername(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }
}
