package com.nextbuy.user.service.Authentication;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

  @Value("${jwt.token.key}")
  private String jwtTokenKey;

  @Value("${jwt.token.expiration}")
  private String jwtTokenExpiration;

  private static final String USER_APP = "USER APP";

  @Override
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    return generateJwtToken(userDetails);
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  private String generateJwtToken(UserDetails userDetails) {
    Date today = new Date();
    Date expiration = new Date(today.getTime() + Long.parseLong(jwtTokenExpiration));

    return Jwts.builder()
            .issuer(USER_APP)
            .subject(userDetails.getUsername())
            .issuedAt(today)
            .expiration(expiration)
            .signWith(getSigningKey())
            .compact();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtTokenKey));
  }

}
