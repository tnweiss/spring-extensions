package dev.tdub.springext.auth.dto;

import dev.tdub.springext.auth.jwt.JwtAuthResponse;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@ToString
@Jacksonized
@EqualsAndHashCode
@RequiredArgsConstructor
public class AuthResponseDto implements JwtAuthResponse {
  private final String accessToken;
  private final String refreshToken;
}
