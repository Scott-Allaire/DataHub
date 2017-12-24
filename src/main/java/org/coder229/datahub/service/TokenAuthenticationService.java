package org.coder229.datahub.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;

@Service
public class TokenAuthenticationService {

    @Value("app.security.secret")
    private String secret;

    public void addAuthentication(HttpServletResponse response, String name) {
        response.addHeader("Authorization", "Bearer " + makeToken(name));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }

        return null;
    }


    public String makeToken(String username) {
        ZonedDateTime expiration = ZonedDateTime.now().plusDays(7);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date.from(expiration.toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
