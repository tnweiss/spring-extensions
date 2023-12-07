package dev.tdub.springext.auth.jwt;

import java.util.UUID;

import dev.tdub.springext.auth.UserPrincipal;

public interface JwtAuthSession {
  UUID getSessionId();
  UUID getRefreshTokenId();
  Long getUserId();
  UserPrincipal toUserPrincipal();
}
