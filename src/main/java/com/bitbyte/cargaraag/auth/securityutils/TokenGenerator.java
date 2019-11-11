package com.bitbyte.cargaraag.auth.securityutils;

import com.bitbyte.cargaraag.auth.entities.Roles;
import com.bitbyte.cargaraag.auth.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import java.util.Date;
import java.util.stream.Collectors;

public class TokenGenerator {

    public static String generateToken(User authenticatedUser) {
        Claims claims = Jwts.claims()
                .setSubject(authenticatedUser.getUserName())
                .setExpiration(DateUtils.addHours(new Date(), 24))
                .setIssuedAt(new Date())
                .setIssuer("");
        claims.put("clientId", "");
        claims.put("userId", authenticatedUser.getId());
        claims.put("userName", authenticatedUser.getUserName());
        claims.put("email", authenticatedUser.getEmail());
        claims.put("firstName", authenticatedUser.getFirstName());
        claims.put("lastName", authenticatedUser.getLastName());
        claims.put("fullName", authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName());
        claims.put("sts", authenticatedUser.getStatus());
        claims.put("group", authenticatedUser.getRoles().stream().map(Roles::getRole).collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "clientSecret")
                .compact();
    }
}
