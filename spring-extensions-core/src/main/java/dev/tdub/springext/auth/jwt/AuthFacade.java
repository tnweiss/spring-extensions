package dev.tdub.springext.auth.jwt;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import dev.tdub.springext.audit.AuditAction;
import dev.tdub.springext.audit.AuditLog;
import dev.tdub.springext.auth.BasicAuthRequest;
import dev.tdub.springext.auth.Network;
import dev.tdub.springext.auth.UserPrincipal;
import dev.tdub.springext.auth.service.NetworkAuthService;
import dev.tdub.springext.auth.service.SessionAuthService;
import dev.tdub.springext.auth.service.UserAuthService;
import dev.tdub.springext.error.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import static dev.tdub.springext.util.serdes.Json.json;

@ConditionalOnProperty(
    value = "springext.auth.authenticators.jwt.enabled",
    havingValue = "true"
)
@Log4j2
@Component
@RequiredArgsConstructor
public class AuthFacade {
  private static final String AUDIT_RESOURCE = "session";

  private final AuthService authService;
  private final UserAuthService userAuthService;
  private final NetworkAuthService networkAuthService;
  private final SessionAuthService sessionAuthService;
  private final AuditLog auditLog;

  public JwtAuthResponse authenticate(BasicAuthRequest request, String remoteAddress) {
    log.debug("Authenticating basic credentials {}", () -> json(request));
    Long userId = userAuthService.authenticate(request.getUsername(), request.getPassword());
    Optional<Network> network = networkAuthService.get(remoteAddress);
    JwtAuthSession jwtAuthSession = sessionAuthService.create(userId, network.orElse(null), remoteAddress);
    JwtAuthResponse response = authService.createTokens(jwtAuthSession);
    auditLog.write(jwtAuthSession.toUserPrincipal(), AuditAction.CREATE, AUDIT_RESOURCE, Map.of("ip", remoteAddress));
    log.debug("Success '{}'", () -> json(response));
    return response;
  }

  public JwtAuthResponse authenticate(RefreshTokenAuthRequest request) {
    log.info("Authenticating refresh token credentials '{}'", () -> json(request));
    RefreshTokenClaims claims = authService.verifyRefreshToken(request.getRefreshToken());
    userAuthService.requireActiveUser(claims.getSub());
    JwtAuthSession jwtAuthSession = sessionAuthService
        .updateRefreshTokenId(claims.getSessionId(), claims.getRefreshId(), UUID.randomUUID());
    JwtAuthResponse response = authService.createTokens(jwtAuthSession);
    auditLog.write(jwtAuthSession.toUserPrincipal(), AuditAction.UPDATE, AUDIT_RESOURCE);
    log.debug("Updated user {} session {} to {}", claims.getSub(), claims.getSessionId(),
        jwtAuthSession.getSessionId());
    log.debug("Success '{}'", () -> json(response));
    return response;
  }

  public void logout(UserPrincipal principal) {
    log.info("Invalidating session {}", principal::getSessionId);
    if (principal.getSessionId() == null) {
      throw new ClientException("Invalid Credentials.");
    }
    sessionAuthService.delete(principal.getSessionId());
    auditLog.write(principal, AuditAction.DELETE, AUDIT_RESOURCE, Map.of("sessionId", principal.getSessionId()));
    log.debug("Success");
  }

  public void invalidateSession(UserPrincipal principal, UUID sessionId) {
    log.info("User '{}' invalidating Session '{}'", principal.getUserId(), sessionId);
    if (!isAuthorizedToDeleteSession(principal, sessionId)) {
      throw new ClientException("Caller is not authorized to delete session.");
    }
    sessionAuthService.delete(sessionId);
    auditLog.write(principal, AuditAction.DELETE, AUDIT_RESOURCE, Map.of("sessionId", sessionId));
    log.debug("Success");
  }

  private boolean isAuthorizedToDeleteSession(UserPrincipal principal, UUID sessionId) {
    if (Objects.equals(principal.getSessionId(), sessionId)) {
      return true;
    }

    return Objects.equals(sessionAuthService.get(sessionId).getUserId(), principal.getUserId());
  }
}
