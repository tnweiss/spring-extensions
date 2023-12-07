package com.ora.web.common.security;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.ora.web.common.serdes.Json;

import dev.tdub.springext.error.dto.ErrorResponseDto;
import dev.tdub.springext.error.RequestIdSupplier;
import dev.tdub.springext.error.exceptions.AuthenticationException;
import dev.tdub.springext.error.exceptions.InternalServerException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class OraAuthenticationFilter extends OncePerRequestFilter {
  public static final String MDC_RID = "RID";
  public static final String MDC_UID = "UID";

  private final List<OraAuthenticator> authenticators;
  private final RequestIdSupplier ridSupplier;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {
    putRIDMdc();

    try {
      if (!isAuthenticated()) {
        authenticate(request);
      }
    } catch (AuthenticationException ex) {
      handleUnauthorized(response);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private void authenticate(HttpServletRequest request) throws AuthenticationException {
    dev.tdub.springext.auth.Authentication authentication = authenticators.stream()
        .filter(a -> a.canAuthenticate(request))
        .findFirst()
        .orElseThrow(AuthenticationException::new)
        .authenticate(request);

    SecurityContextHolder.getContext()
        .setAuthentication(authentication);

    putUIDMdc(authentication);
  }

  private void handleUnauthorized(HttpServletResponse response) {
    try {
      response.getWriter().write(Json.json(new ErrorResponseDto("Invalid Credentials.", ridSupplier.get())));
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType("application/json");
    } catch (Exception ex) {
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      throw new InternalServerException(ex);
    }
  }

  private void putRIDMdc() {
    String rid = String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 999999999));
    MDC.put(MDC_RID, rid);
  }

  private void putUIDMdc(dev.tdub.springext.auth.Authentication authentication) {
    MDC.put(MDC_UID, authentication.getPrincipal().getUserId().toString());
  }

  private boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null && authentication.isAuthenticated();
  }
}
