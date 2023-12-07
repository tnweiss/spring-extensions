package com.ora.web.common.security;

import dev.tdub.springext.auth.Authentication;
import dev.tdub.springext.error.exceptions.AuthenticationException;

import jakarta.servlet.http.HttpServletRequest;

public interface OraAuthenticator {
  boolean canAuthenticate(HttpServletRequest request);
  Authentication authenticate(HttpServletRequest request) throws AuthenticationException;
}
