package dev.tdub.springext.auth;

import java.util.UUID;

import dev.tdub.springext.auth.jwt.JwtAuthSession;
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
}
