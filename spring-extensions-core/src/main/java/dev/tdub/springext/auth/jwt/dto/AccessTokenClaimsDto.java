package dev.tdub.springext.auth.jwt.dto;

import java.util.UUID;

import dev.tdub.springext.auth.jwt.AccessTokenClaims;
import io.jsonwebtoken.Claims;
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
public class AccessTokenClaimsDto implements AccessTokenClaims {
  private final Long sub;
  private final UUID sessionId;

  public AccessTokenClaimsDto(Claims claims) {
    this.sub = Long.parseLong(claims.get("sub", String.class));
    this.sessionId = UUID.fromString(claims.get("sessionId", String.class));
  }
}
