package com.spboot.app.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.SecureUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

/**
 * jwt工具类,将数据进行加密和解密操作
 * @author tanwubo
 */
@Component
public class JwtTokenUtils {

    // JWT 过期时间（秒），默认7天
    private static final long EXPIRATION_SECONDS = 7 * 24 * 3600;

    public SecretKey key;

    public JwtTokenUtils() {
        key = (SecretKey) readObj();
        if (key == null) {
            // 优先读取环境变量 JWT_SECRET，否则使用内置种子
            String secret = System.getenv("JWT_SECRET");
            if (secret == null || secret.isEmpty()) {
                secret = "bishe-jwt-secret-key-2024";
            }
            byte[] seed = SecureUtil.sha256().digest(secret);
            key = Keys.hmacShaKeyFor(seed);
            writeObj(key);
        }
    }

    // 序列化到 .jwtkey 文件（已加入 gitignore）
    public static void writeObj(Object p) {
        try {
            File file = new File(".jwtkey");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(p);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 反序列化
    public static Object readObj() {
        Object p = null;
        try {
            File file = new File(".jwtkey");
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            try {
                p = objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
            }
        } catch (IOException e) {
            // 首次运行，密钥文件不存在，正常
        }
        return p;
    }

    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return Jwts.parser().setSigningKey(this.key).parseClaimsJws(token).getBody();
    }

    public String generateToken(Object userId, Map<String, Object> claims) {
        return doGenerateToken(claims, String.valueOf(userId));
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = DateTime.now();
        final Date expirationDate = new Date(createdDate.getTime() + EXPIRATION_SECONDS * 1000);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(this.key)
            .compact();
    }
}
