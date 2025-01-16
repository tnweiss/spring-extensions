package dev.tdub.springext.auth.jwt;

public interface RefreshTokenGenerator {
  String create(RefreshTokenClaims claims);
}
