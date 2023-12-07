package dev.tylerweiss.springext.error.exceptions;

public class AuthorizationException extends RuntimeException {
  public AuthorizationException(String ex) {
    super(ex);
  }
}
