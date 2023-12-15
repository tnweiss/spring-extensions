package dev.tdub.springext.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserPrincipal extends UserDetails {
  Long getUserId();

  UUID getSessionId();
}
