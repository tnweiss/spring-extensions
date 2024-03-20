package dev.tdub.springext.auth;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import dev.tdub.springext.error.RequestIdSupplier;
import org.slf4j.MDC;
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
  @Value("${springext.auth.unprotected-resources}")
  public List<String> unprotectedResources;

  @Bean
  public PasswordEncoder hasher() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RequestIdSupplier requestIdSupplier() {
    return () -> MDC.get(AuthenticationFilter.MDC_RID);
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
                                     AuthenticationFilter authenticationFilter) throws Exception {
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
