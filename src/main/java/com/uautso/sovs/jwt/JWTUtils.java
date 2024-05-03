package com.uautso.sovs.jwt;

import com.uautso.sovs.servImpl.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${tz.go.pmo.jwtkey}")
    private String key;

    Logger logger = LoggerFactory.getLogger(JWTUtils.class);


    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", new ArrayList<>())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()),SignatureAlgorithm.HS256)
                .compact();

    }



    public String getUserNameFromJwtToken(String token) {
        String username =  Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody().getSubject();
        logger.debug(username);
        return username;
    }

    public String getIdFromJwtToken(String token){
        String id =  Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody().getSubject();
        logger.debug(id);
        return id;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: "+e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: "+e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token: "+e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: "+e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}"+e.getMessage());
        }

        return false;
    }


}
