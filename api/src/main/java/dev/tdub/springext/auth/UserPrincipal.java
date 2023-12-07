package dev.tdub.springext.auth;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserPrincipal extends UserDetails {
  Long getUserId();

  UUID getSessionId();
}
