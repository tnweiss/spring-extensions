package dev.tdub.springext.error;

public interface ErrorLogger {
  void clientError(Throwable throwable);
  void serverError(Throwable throwable);
}
