package com.devculi.sway.manager.service.security;

import com.devculi.sway.utils.security.JWTUtils;
import com.devculi.sway.utils.security.Protector;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {



//  public String generateToken(CustomUserDetails userDetails) {
//    Objects.requireNonNull(userDetails.getUser().getId());
//    Date now = new Date();
//    Date expiration = new Date(now.getTime() + JWT_EXPIRATION);
//    return getJwts(userDetails.getUser().getId().toString(), now, expiration);
//  }
//
//  public String getJwts(String subject, Date issueAt, Date expiryDate) {
//    return Jwts.builder()
//        .setSubject(subject)
//        .setIssuedAt(issueAt)
//        .setExpiration(expiryDate)
//        .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
//        .compact();
//  }
//
//  public String generateToken(CustomUserDetails userDetails, long timeToLive) {
//    Date now = new Date();
//    Date expiration = new Date(now.getTime() + timeToLive);
//    return getJwts(userDetails.getUser().getId().toString(), now, expiration);
//  }
//
//  public UUID getUserIdFromToken(final String token) {
//    Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
//    return UUID.fromString(claims.getSubject());
//  }
//
  public String getUsernameFromToken(final String token) {
    return JWTUtils.getClaimFromToken(token, Claims::getSubject);
  }
//
//  public Date getExpirationDateFromToken(final String token) {
//    return getClaimFromToken(token, Claims::getExpiration);
//  }
//
//
//  private Claims getAllClaimsFromToken(final String token) {
//    return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
//  }
//
//  public Boolean isTokenExpired(final String token) {
//    final Date expiration = getExpirationDateFromToken(token);
//    return expiration.before(new Date());
//  }
//
  public boolean validateToken(final String authToken) {
    try {
      Jwts.parser().setSigningKey(JWTUtils.SECRET_KEY).parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException
        | ExpiredJwtException
        | UnsupportedJwtException
        | IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    return false;
  }

}
