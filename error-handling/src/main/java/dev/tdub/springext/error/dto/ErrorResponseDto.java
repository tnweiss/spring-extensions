package dev.tylerweiss.springext.error.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ErrorResponseDto implements ErrorResponse {
  private final String requestId;
  private final String message;
  private final Instant timestamp;

  public ErrorResponseDto(String message, String requestId) {
    this.message = message;
    this.requestId = requestId;
    this.timestamp = Instant.now();
  }
}
