package com.marvic.factsigner.security;

import com.marvic.factsigner.EnvProperties;
import com.marvic.factsigner.exception.APIException;
import com.marvic.factsigner.util.Utils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Generate JWT Add and Validaate Custom Claims
 *
 * https://www.appsdeveloperblog.com/add-and-validate-custom-claims-in-jwt
 *
 * https://www.javainuse.com/spring/jwt
 * https://www.javainuse.com/spring/boot-jwt
 */
@Component
public class JwtTokenProvider {

    @Autowired
    private EnvProperties env;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        long millis = NumberUtils.toLong(env.getJwtExpirationMilliseconds(), EnvProperties.ONE_DAY_MS);

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + millis);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(env.getJwtSecret())
        );
    }

    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new APIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }

}
