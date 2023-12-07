package dev.tdub.springext.auth.jwt;

import java.util.UUID;

import dev.tdub.springext.auth.UserPrincipal;
import dev.tdub.springext.auth.UserPrincipalDto;
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
public class JwtAuthSessionDto implements JwtAuthSession {
  private final UUID sessionId;
  private final UUID refreshTokenId;
  private final Long userId;

  @Override
  public UserPrincipal toUserPrincipal() {
    return new UserPrincipalDto(userId, sessionId);
  }
}
