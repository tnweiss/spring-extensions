package dev.tdub.springext.auth;

import java.util.Optional;
import java.util.UUID;

import com.ora.web.common.dto.auth.AuthResponseDto;
import com.ora.web.common.dto.auth.RefreshTokenClaimsDto;
import com.ora.web.common.dto.logging.AuditAction;
import com.ora.web.common.dto.logging.AuditResource;
import com.ora.web.common.dto.network.Network;
import com.ora.web.common.dto.session.Session;
import com.ora.web.common.dto.user.User;
import com.ora.web.service.AuthService;
import com.ora.web.service.NetworkService;
import com.ora.web.service.SessionService;
import com.ora.web.service.UserService;

import dev.tdub.springext.auth.jwt.RefreshTokenAuthRequest;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import static com.ora.web.common.serdes.Json.json;

@Component
@CustomLog
@RequiredArgsConstructor
public class AuthFacade {
  private final UserService userService;
  private final NetworkService networkService;
  private final SessionService sessionService;
  private final AuthService authService;

  public AuthResponseDto authenticate(BasicAuthRequest request, String remoteAddress) {
    log.info("Authenticating basic credentials {}", () -> json(request));
    User user = userService.authenticate(request.getUsername(), request.getPassword());
    Optional<Network> network = networkService.get(remoteAddress);
    Session session = sessionService.create(user, network.orElse(null), remoteAddress);
    AuthResponseDto response = authService.createTokens(session);
    log.audit(AuditAction.CREATE, AuditResource.SESSION, json(request), session.getSessionId());
    return response;
  }

  public AuthResponseDto authenticate(RefreshTokenAuthRequest request) {
    log.info("Authenticating refresh token credentials {}", () -> json(request));
    RefreshTokenClaimsDto claims = authService.verifyRefreshToken(request.getRefreshToken());
    userService.requireActiveUser(claims.getSub());
    Session session = sessionService.updateRefreshTokenId(claims.getSessionId(), claims.getRefreshId(), UUID.randomUUID());
    AuthResponseDto response = authService.createTokens(session);
    log.audit(AuditAction.UPDATE, AuditResource.SESSION, request, null);
    return response;
  }

  public void logout(UserPrincipal principal) {
    log.info("Invalidating session {}", principal::getSessionId);
    sessionService.delete(principal.getSessionId());
    log.audit(AuditAction.DELETE, AuditResource.SESSION, principal.getSessionId(), null);
  }
}
