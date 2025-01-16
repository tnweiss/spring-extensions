package dev.tdub.springext.error;

import lombok.*;

import java.time.Instant;

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
