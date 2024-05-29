package com.huce.project.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;

public class JWTConfig {

    private static final String SECRET_KEY = "yT@z5yZrTjVnQpVmMt2bTs!nE4r7wNx!U9vJyUe5iWd1m2oQx@eThXgVr2cLzP";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    private static final String PREFIX_TOKEN = "Bearer ";

    public static String generateToken(HttpServletResponse response, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        response.addHeader("Authorization", PREFIX_TOKEN + "" + token);
        return token;
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}