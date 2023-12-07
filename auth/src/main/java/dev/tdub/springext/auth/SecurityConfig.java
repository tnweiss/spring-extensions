package com.ora.web.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.ora.web.common.dataaccess.user.UserRepo;
import com.ora.web.common.security.OraAuthenticator;
import com.ora.web.common.security.jwt.AccessTokenGenerator;
import com.ora.web.common.security.authenticator.DefaultUserAuthFilter;
import com.ora.web.common.security.authenticator.JwtAuthFilter;
import com.ora.web.common.security.OraAuthenticationFilter;
import com.ora.web.common.security.jwt.RefreshTokenGenerator;
import com.ora.web.service.AuthService;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${ora.security.authentication.required}")
  public boolean isAuthRequired;

  @Value("${ora.security.jwt.secret}")
  public String jwtSecret;

  @Value("${ora.security.jwt.algorithm}")
  public String jwtSecretAlgorithm;

  @Value("${ora.security.jwt.issuer}")
  public String jwtIssuer;

  @Value("${ora.security.jwt.access-token-expiration-minutes}")
  public Long jwtAccessTokenExpirationMinutes;

  @Value("${ora.security.jwt.refresh-token-expiration-minutes}")
  public Long jwtRefreshTokenExpirationMinutes;

  @Value("${ora.security.unprotected-resources}")
  public List<String> unprotectedResources;

  @Bean
  public PasswordEncoder hasher() {
    return new BCryptPasswordEncoder();
  }

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

  @Bean
  public boolean isAuthRequired() {
    return isAuthRequired;
  }

  @Order(0)
  @Bean
  public SecurityFilterChain permit(HttpSecurity http) throws Exception {
    List<Map.Entry<Predicate<String>, Predicate<String>>> unprotected = unprotectedResources.stream()
        .map(s -> s.split(":"))
        .map(s -> Map.entry(Pattern.compile(s[0]).asPredicate(), Pattern.compile(s[1]).asPredicate()))
        .toList();

    return http
        .securityMatcher(r -> unprotected.stream().anyMatch(u -> u.getKey().test(r.getMethod()) && u.getValue().test(r.getRequestURI()))) //unprotected.contains(Map.entry(r.getMethod(), r.getRequestURI())))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(c -> c.anyRequest().permitAll())
        .build();
  }

  @Order(1)
  @Bean
  public SecurityFilterChain protect(HttpSecurity http,
                                     OraAuthenticationFilter authenticationFilter) throws Exception {
    return http
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(c -> c.anyRequest().authenticated())
        .exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter.class)
        .build();
  }
}
