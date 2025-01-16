package dev.tdub.springext.error.exceptions;

public class AuthenticationException extends org.springframework.security.core.AuthenticationException {
  public AuthenticationException() {
    super("Invalid Credentials");
  }
}
