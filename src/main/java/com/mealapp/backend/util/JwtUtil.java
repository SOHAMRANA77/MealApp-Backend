package com.mealapp.backend.util;

import com.mealapp.backend.entities.Booking;
import com.mealapp.backend.enums.MenuType;
import com.mealapp.backend.enums.UserRole;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@PropertySource("classpath:application.properties")
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

//    public String generateToken(String email) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("email", email);
//        return createToken(claims);
//    }
    public String generateToken(Long C_id, LocalDate C_date, Booking B_id, MenuType menuType){
        Map<String, Object> claims = new HashMap<>();
        claims.put("Coupon_ID",C_id);
        claims.put("LocalDate",C_date);
        claims.put("Booking_ID",B_id.getId());
        claims.put("MenuType",menuType);
        return createToken(claims,2L);
    }
    public String generateToken(String email, Long id, UserRole userRole){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email",email);
        claims.put("id",id);
        claims.put("UserRole", userRole);
        return createToken(claims,5L);
    }

    private String createToken(Map<String, Object> claims,Long hour) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * hour))
                    .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                    .compact();
        } catch (JwtException e) {
            System.out.println(e + " \"JWT creation failed\"\n\n\n");
            throw e;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
