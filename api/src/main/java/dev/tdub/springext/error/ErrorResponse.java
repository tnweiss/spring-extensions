package dev.tdub.springext.error.dto;

import java.time.Instant;

public interface ErrorResponse {
  String getRequestId();
  Instant getTimestamp();
  String getMessage();
}
