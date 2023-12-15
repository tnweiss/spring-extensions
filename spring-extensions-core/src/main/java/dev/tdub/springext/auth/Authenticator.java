package dev.tdub.springext.auth;

import jakarta.servlet.http.HttpServletRequest;

public interface Authenticator {
  boolean canAuthenticate(HttpServletRequest request);
  Authentication authenticate(HttpServletRequest request);
}
