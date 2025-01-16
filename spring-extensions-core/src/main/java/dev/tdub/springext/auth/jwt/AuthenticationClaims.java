package dev.tdub.springext.auth.jwt;

import java.util.Map;

public interface AuthenticationClaims {
  Long getSub();

  default Map<String, Object> getAdditionalClaims() {
    return Map.of();
  }
}
