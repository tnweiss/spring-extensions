package dev.tdub.springext.auth.jwt;

import java.util.UUID;

public interface AccessTokenClaims {
  Long getSub();
  UUID getSessionId();
}
