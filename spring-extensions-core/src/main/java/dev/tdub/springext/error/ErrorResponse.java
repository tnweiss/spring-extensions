package dev.tdub.springext.error;

import java.time.Instant;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dev.tdub.springext.util.serdes.InstantSerializer;

public interface ErrorResponse {
  String getRequestId();
  @JsonSerialize(using = InstantSerializer.class)
  Instant getTimestamp();
  String getMessage();
}
