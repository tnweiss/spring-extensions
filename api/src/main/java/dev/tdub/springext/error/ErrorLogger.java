package dev.tdub.springext.error.dto;

public interface ErrorLogger {
  void clientError(Throwable throwable);
  void serverError(Throwable throwable);
}
