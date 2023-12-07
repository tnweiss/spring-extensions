package dev.tylerweiss.springext.error.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String ex) {
    super(ex);
  }
}
