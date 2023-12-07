package dev.tdub.springext.error.exceptions;

public class AuthorizationException extends RuntimeException {
  public AuthorizationException(String ex) {
    super(ex);
  }
}
