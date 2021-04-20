package com.example.alwayswin.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: JWT
 * @Description: Json Web Token, for user validation
 * @Author: SQ
 * @Date: 2021-4-19
 */

@Configuration
public class JWT {
    private static Logger logger = LoggerFactory.getLogger(JWT.class);
    private final String stringKey = "zI8tyIe6Mdze0cGs";

    private Key key;
    private long expireTime;  // millisecond

    public JWT() {

        // 切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去
        key = generateKey();
        expireTime = 6000000;
    }

    // 生成token
    public String generateToken(Long uid) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expireTime * 10);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(uid + "")
                .setIssuer("AlwaysWin server")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();
    }


    public Claims getClaimByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String[] header = token.split("Bearer");
        token = header[1];  // In header, {Bearer: token}
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build().parseClaimsJws(token).getBody();
        }catch (Exception e){
            if(e instanceof ExpiredJwtException){
                return new Claims() {
                    @Override
                    public String getIssuer() {
                        return null;
                    }

                    @Override
                    public Claims setIssuer(String s) {
                        return null;
                    }

                    @Override
                    public String getSubject() {
                        return null;
                    }

                    @Override
                    public Claims setSubject(String s) {
                        return null;
                    }

                    @Override
                    public String getAudience() {
                        return null;
                    }

                    @Override
                    public Claims setAudience(String s) {
                        return null;
                    }

                    @Override
                    public Date getExpiration() {
                        return new Date(0);
                    }

                    @Override
                    public Claims setExpiration(Date date) {
                        return null;
                    }

                    @Override
                    public Date getNotBefore() {
                        return null;
                    }

                    @Override
                    public Claims setNotBefore(Date date) {
                        return null;
                    }

                    @Override
                    public Date getIssuedAt() {
                        return null;
                    }

                    @Override
                    public Claims setIssuedAt(Date date) {
                        return null;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public Claims setId(String s) {
                        return null;
                    }

                    @Override
                    public <T> T get(String s, Class<T> aClass) {
                        return null;
                    }

                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean containsKey(Object o) {
                        return false;
                    }

                    @Override
                    public boolean containsValue(Object o) {
                        return false;
                    }

                    @Override
                    public Object get(Object o) {
                        return null;
                    }

                    @Override
                    public Object put(String s, Object o) {
                        return null;
                    }

                    @Override
                    public Object remove(Object o) {
                        return null;
                    }

                    @Override
                    public void putAll(Map<? extends String, ?> map) {

                    }

                    @Override
                    public void clear() {

                    }

                    @Override
                    public Set<String> keySet() {
                        return null;
                    }

                    @Override
                    public Collection<Object> values() {
                        return null;
                    }

                    @Override
                    public Set<Entry<String, Object>> entrySet() {
                        return null;
                    }
                };
            }
            logger.debug("Token error ", e);
            return null;
        }
    }


    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    private Key generateKey() {
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(stringKey);

        // 根据给定的字节数组使用AES加密算法构造一个密钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

}
