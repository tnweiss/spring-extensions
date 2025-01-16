package dev.tdub.springext.auth.jwt;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Date;
import java.util.Objects;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@ConditionalOnProperty(
    value = "springext.auth.authenticators.jwt.enabled",
    havingValue = "true"
)
@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {
  @Value("${springext.auth.authenticators.jwt.secret}")
  public String jwtSecret;

  @Value("${springext.auth.authenticators.jwt.algorithm}")
  public String jwtSecretAlgorithm;

  @Value("${springext.auth.authenticators.jwt.issuer}")
  public String jwtIssuer;

  @Value("${springext.auth.authenticators.jwt.access-token-expiration-minutes}")
  public Long jwtAccessTokenExpirationMinutes;

  @Value("${springext.auth.authenticators.jwt.refresh-token-expiration-minutes}")
  public Long jwtRefreshTokenExpirationMinutes;

  @Bean
  public JwtParser parser() {
    return Jwts.parser()
        .verifyWith(jwtSecret())
        .requireIssuer(jwtIssuer)
        .build();
  }

  @Bean
  public AccessTokenGenerator accessTokenGenerator() {
    SecretKey key = jwtSecret();
    return claims -> Jwts.builder()
        .issuer(jwtIssuer)
        .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * jwtAccessTokenExpirationMinutes)))
        .issuedAt(new Date(System.currentTimeMillis()))

        .subject(Objects.toString(claims.getSub()))
        .claim("sessionId", Objects.toString(claims.getSessionId()))
        .claim("additionalClaims", claims.getAdditionalClaims())

        .signWith(key)
        .compact();
  }

  @Bean
  public RefreshTokenGenerator refreshTokenGenerator() {
    SecretKey key = jwtSecret();
    return claims -> Jwts.builder()
        .issuer(jwtIssuer)
        .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * jwtRefreshTokenExpirationMinutes)))
        .issuedAt(new Date(System.currentTimeMillis()))

        .subject(Objects.toString(claims.getSub()))
        .claim("sessionId", Objects.toString(claims.getSessionId()))
        .claim("refreshId", Objects.toString(claims.getRefreshId()))

        .signWith(key)
        .compact();
  }

  @Bean
  public SecretKey jwtSecret() {
    return new SecretKeySpec(
        Decoders.BASE64.decode(jwtSecret),
        jwtSecretAlgorithm
    );
  }
}
