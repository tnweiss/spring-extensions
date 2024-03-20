package dev.tdub.springext.auth.jwt;

import java.util.Map;
import java.util.UUID;

public interface AccessTokenClaims {
  Long getSub();
  UUID getSessionId();

  Map<String, Object> getAdditionalClaims();
}
