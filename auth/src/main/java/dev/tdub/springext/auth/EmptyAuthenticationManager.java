package dev.tdub.springext.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

// Used to disable default authentication management
@Component
public class EmptyAuthenticationManager implements AuthenticationManager {
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return authentication;
  }
}
