package dev.tdub.springext.auth.service;

import dev.tdub.springext.auth.Network;
import dev.tdub.springext.auth.jwt.JwtAuthSession;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface SessionAuthService {
  JwtAuthSession create(Long userId, @Nullable Network network, String ip);
  JwtAuthSession updateRefreshTokenId(UUID sessionId, UUID currentRefreshTokenId, UUID newRefreshTokenId);
  void delete(UUID sessionId);
  JwtAuthSession get(UUID sessionId);
}
