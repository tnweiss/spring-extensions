package dev.tdub.springext.error;

import java.time.Instant;

public interface ErrorResponse {
  String getRequestId();
  Instant getTimestamp();
  String getMessage();
}
