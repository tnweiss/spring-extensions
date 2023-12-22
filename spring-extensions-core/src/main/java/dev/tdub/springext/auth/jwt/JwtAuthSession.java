package dev.tdub.springext.auth.jwt;

import dev.tdub.springext.auth.UserPrincipal;

import java.util.UUID;

public interface JwtAuthSession {
  UUID getSessionId();
  UUID getRefreshTokenId();
  Long getUserId();
  UserPrincipal toUserPrincipal();
}
