package dev.tdub.springext.auth.service;

import java.util.Optional;

import dev.tdub.springext.auth.jwt.AuthenticationClaims;

public interface UserAuthService {
  Optional<AuthenticationClaims> authenticate(String username, String password);
  Optional<AuthenticationClaims> getActiveUserClaims(Long userId);
}
