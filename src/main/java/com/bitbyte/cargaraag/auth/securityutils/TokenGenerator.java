package com.bitbyte.cargaraag.auth.securityutils;

import com.bitbyte.cargaraag.auth.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import java.util.Date;

public class TokenGenerator {

    public static String generateToken(User authenticatedUser) {
        Claims claims = Jwts.claims()
                .setSubject(authenticatedUser.getUserName())
                .setExpiration(DateUtils.addHours(new Date(), 24))
                .setIssuedAt(new Date())
                .setIssuer("");
        claims.put("clientId", "");
        claims.put("email", authenticatedUser.getUserName());
        claims.put("firstName", authenticatedUser.getFirstName());
        claims.put("lastName", authenticatedUser.getLastName());
        claims.put("fullName", authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName());
        claims.put("sts", authenticatedUser.getStatus());
        authenticatedUser.getRoles().forEach(role -> claims.put("group", role.getRole()));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "clientSecret")
                .compact();
    }
}
