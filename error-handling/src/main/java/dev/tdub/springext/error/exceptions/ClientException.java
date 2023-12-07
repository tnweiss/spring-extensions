package dev.tylerweiss.springext.error.exceptions;

public class ClientException extends RuntimeException {
  public ClientException(Exception ex) {
    super(ex);
  }

  public ClientException(String ex) {
    super(ex);
  }
}
