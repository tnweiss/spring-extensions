package dev.tdub.springext.auth.jwt;

public interface AccessTokenGenerator {
  String create(AccessTokenClaims claims);
}
