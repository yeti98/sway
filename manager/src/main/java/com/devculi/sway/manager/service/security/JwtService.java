package com.devculi.sway.manager.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtService {
  public static final long JWT_TOKEN_VALIDITY = 7 * 86400; // a week
  public static final String SECRET_KEY = "devculi_wq3Dr8O5wrkCSybDkQ==1_2020@)@)";
  private static final long serialVersionUID = -2550185165626007488L;

  // generate token for user
  public static String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("devculi", "sway");
    return doGenerateToken(claims, username);
  }

  // while creating the token -
  // 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
  // 2. Sign the JWT using the HS512 algorithm and secret key.
  // 3. According to JWS Compact
  // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
  //   compaction of the JWT to a URL-safe string
  private static String doGenerateToken(Map<String, Object> claims, String subject) {

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public static void main(String[] args) {
    //    System.out.println(JWTUtils.generateToken("trangnh"));
    //    System.out.println(JWTUtils.generateToken("trangnh"));

    System.out.println(
        validateToken(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmFuZ25oIiwiZGV2Y3VsaSI6InN3YXkiLCJleHAiOjE1OTUxMzMyMDEsImlhdCI6MTU5NTEzMjU5Nn0.pqcY5AHSjnAxMNEZbrTvx2hfIbfl7cCXAXs9UTGQMbcJS22PrJgiTrCt6g-JWmcfIlUSDUW980wZ3D_KfmYNTw",
            "trangnh1"));
  }

  public static String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public static Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  // check if the token has expired
  public static Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  // validate token
  public static Boolean validateToken(String token, String username) {
    final String usernameFromToken = getUsernameFromToken(token);
    return (username.equals(usernameFromToken) && !isTokenExpired(token));
  }

  public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  // for retrieveing any information from token we will need the secret key
  private static Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }
}
