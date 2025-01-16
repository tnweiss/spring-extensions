
package dev.tdub.springext.auth.jwt.dto;

import java.util.UUID;

import dev.tdub.springext.auth.jwt.RefreshTokenClaims;
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
public class RefreshTokenClaimsDto implements RefreshTokenClaims {
  private final Long sub;
  private final UUID sessionId;
  private final UUID refreshId;

  public RefreshTokenClaimsDto(Claims claims) {
    this.sub = Long.parseLong(claims.getSubject());
    this.refreshId = UUID.fromString(claims.get("refreshId", String.class));
    this.sessionId = UUID.fromString(claims.get("sessionId", String.class));
  }
}
