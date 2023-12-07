package dev.tdub.springext.auth.jwt;

import java.util.UUID;

public interface JwtAuthSession {
  UUID getSessionId();
  UUID getRefreshTokenId();
  Long getUserId();
}
