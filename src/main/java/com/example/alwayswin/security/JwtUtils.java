package com.example.alwayswin.security;

import com.example.alwayswin.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

/**
 * @ClassName: JwtUtils
 * @Description: Json Web Token, for user validation
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Configuration
public class JwtUtils {
    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // 切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去
    private final static SecretKey key = secretKeyFor(SignatureAlgorithm.HS512);

    private final static long expireTime = 600000000;  // millisecond, 10000 min, about 7 days

    /*
     * @Description: 根据uid和role生成token
     * @Param: [user]
     * @Return: java.lang.String
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    public static String generateToken(User user) {
        if (user == null)
            return null;
        try {
            Date nowDate = new Date();
            Date expireDate = new Date(nowDate.getTime() + expireTime);
            String token = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setIssuer("AlwaysWin server")  // optional
                    .setIssuedAt(nowDate)  // optional
                    .setExpiration(expireDate)
                    .setSubject(user.getUsername())
                    .setAudience(user.getUid() + "")    // 根据uid 设置 Token 的接受者
                    .claim("role", user.getRole())  // 在claim 中增加role
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    public static Claims getClaimFromToken(String token) {
        if (token.isEmpty())
            return null;
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e) {
            if (e instanceof ExpiredJwtException)
                logger.debug("Expired token", e.getMessage());
            else
                logger.warn("Invalid token", e.getMessage());
        }
        return null;
    }

    /*
     * @Description: 验证Token ，并获取到用户名和用户权限信息
     * @Param: [token]
     * @Return: com.example.alwayswin.entity.User
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    public static User validateToken(String token) {
        try {
            // 解密 Token，获取 Claims 主体
            Claims claims = getClaimFromToken(token);
            if (claims == null) {
                logger.warn("Invalid token");
                return null;
            }
            // 判断是否过期
            if (claims.getExpiration().before(new Date())) {
                logger.debug("Expired Token");
                return null;
            }
            User user = new User();
            user.setUid(Integer.valueOf(claims.getAudience()));
            user.setUsername(claims.get("username").toString());
            user.setRole(claims.get("role").toString());
            return user;
        } catch (Exception e) {
            logger.debug("Invalid token", e.getMessage());
            return null;
        }
    }

    public static String getTokenFromHeader(String authHeader) {
        String token = null;
        // Token 开头部分 默认 Bearer 开头, 注意空格
        final String bearer = "Bearer ";
        if (authHeader != null && authHeader.startsWith(bearer)) {
            // 抽取token
             token = authHeader.substring(bearer.length());
        }
        return token;
    }

}
