package dev.tdub.springext.auth.jwt;

import java.util.UUID;

import dev.tdub.springext.auth.Authentication;
import dev.tdub.springext.auth.BasicAuthRequestDto;
import dev.tdub.springext.auth.jwt.dto.RefreshTokenAuthRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty(
    value = "springext.auth.authenticators.jwt.enabled",
    havingValue = "true"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthController {
  private final AuthFacade authFacade;

  @Operation(summary = "Create a new Session with username and password")
  @PostMapping("/auth/tokens")
  public JwtAuthResponse authenticate(@RequestBody BasicAuthRequestDto authRequest, HttpServletRequest request) {
    return authFacade.authenticate(authRequest, request.getRemoteAddr());
  }

  @Operation(summary = "Refresh an existing Session")
  @PostMapping("/auth/tokens/refresh")
  public JwtAuthResponse authenticate(@RequestBody RefreshTokenAuthRequestDto authRequest) {
    return authFacade.authenticate(authRequest);
  }

  @Operation(summary = "Invalidate the callers Session")
  @DeleteMapping("/auth/tokens")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(Authentication authentication) {
    authFacade.logout(authentication.getPrincipal());
  }

  @Operation(summary = "Invalidate a users Session")
  @DeleteMapping("/sessions/{sessionId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(Authentication authentication, @PathVariable("sessionId") UUID sessionId) {
    authFacade.invalidateSession(authentication.getPrincipal(), sessionId);
  }
}
