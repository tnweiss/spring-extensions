package dev.tdub.springext.error.exceptions;

public class UnprocessableException extends RuntimeException {
  public UnprocessableException(String ex) {
    super(ex);
  }
}
