package com.mohamed.halim.jwtservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
  private final RabbitTemplate rabbit;
  private Jackson2JsonMessageConverter converter;
  private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

  @RabbitListener(queues = "jwt.token.extract.username")
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @RabbitListener(queues = "jwt.token.generate")
  public String generateToken(@Payload User userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  @RabbitListener(queues = "jwt.token.verify")
  public void verifyToken(Message message) {
    String token = (String) converter.fromMessage(message);
    var props = message.getMessageProperties();
    User user = rabbit.convertSendAndReceiveAsType(
        "profile", "profile.load.profile", extractUsername(token), new ParameterizedTypeReference<User>() {
        });
    if (user == null || !isTokenValid(token, user)) {
      props.setHeader("error", "username or password is incorrect");
    }
    rabbit.send(props.getReplyTo(), converter.toMessage("", props));
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14)))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @RabbitListener(queues = "jwt.token.validation")
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}