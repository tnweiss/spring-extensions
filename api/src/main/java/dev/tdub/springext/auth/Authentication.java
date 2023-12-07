package dev.tdub.springext.auth.jwt;

import java.util.UUID;

import dev.tdub.springext.auth.UserPrincipal;

public interface Authentication extends org.springframework.security.core.Authentication {
  Long callerId();

  UUID sessionId();

  @Override
  UserPrincipal getPrincipal();
}
