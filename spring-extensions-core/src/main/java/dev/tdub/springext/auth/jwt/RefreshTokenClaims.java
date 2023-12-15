package dev.tdub.springext.auth.jwt;

import java.util.UUID;

public interface RefreshTokenClaims {
  Long getSub();
  UUID getSessionId();
  UUID getRefreshId();
}
