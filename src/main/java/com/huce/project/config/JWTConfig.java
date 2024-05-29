package com.huce.project.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.function.Function;

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
    public static Boolean validateToken(String token, String name) {
        final String username = extractUsername(token);
        return (username.equals(name) && !isTokenExpired(token));
    }
    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
        public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}