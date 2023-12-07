package dev.tdub.springext.auth;

import java.util.UUID;

public interface Authentication extends org.springframework.security.core.Authentication {
  Long callerId();

  UUID sessionId();

  @Override
  UserPrincipal getPrincipal();
}
