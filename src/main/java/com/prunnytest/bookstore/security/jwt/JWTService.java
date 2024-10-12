package com.prunnytest.bookstore.security.jwt;

import com.prunnytest.bookstore.model.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    private final String SECRET_KEY = "363f414bc19f428f4b4595ba9cddfd10059a3d8f67868f82492fe5a50f9743e4";

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user){
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);

    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(Customer customer){
        return Jwts
                .builder()
                .subject(customer.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*3600*1000))
                .signWith(getSignInKey())
                .compact();

    }

    private SecretKey getSignInKey() {
        byte[] keyByte = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}