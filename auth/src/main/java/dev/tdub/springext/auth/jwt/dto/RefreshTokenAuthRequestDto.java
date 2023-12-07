package dev.tdub.springext.auth.dto;

import dev.tdub.springext.auth.jwt.RefreshTokenAuthRequest;
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
public class RefreshTokenAuthRequestDto implements RefreshTokenAuthRequest {
  private final String refreshToken;
}
