package com.example.WatchShop.util;

import com.example.WatchShop.model.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenGenerator {
    public static String generateAccessToken(Users user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 giờ
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }
}
