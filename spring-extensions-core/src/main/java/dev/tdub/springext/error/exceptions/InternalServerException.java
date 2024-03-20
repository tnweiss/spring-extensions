package dev.tdub.springext.error.exceptions;

public class InternalServerException extends RuntimeException {
  public InternalServerException(Exception ex) {
    super("Internal error, please contact the ora team", ex);
  }

  public InternalServerException(String ex) {
    super(ex);
  }
}
