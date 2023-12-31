package dev.tdub.springext.auth.jwt;

import dev.tdub.springext.auth.Authentication;
import dev.tdub.springext.auth.BasicAuthRequestDto;
import dev.tdub.springext.auth.jwt.dto.RefreshTokenAuthRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("api/v1/auth")
public class AuthController {
  private final AuthFacade authFacade;

  @PostMapping("/tokens")
  public JwtAuthResponse authenticate(@RequestBody BasicAuthRequestDto authRequest, HttpServletRequest request) {
    return authFacade.authenticate(authRequest, request.getRemoteAddr());
  }

  @PostMapping("/tokens/refresh")
  public JwtAuthResponse authenticate(@RequestBody RefreshTokenAuthRequestDto authRequest) {
    return authFacade.authenticate(authRequest);
  }

  @DeleteMapping("/tokens")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(Authentication authentication) {
    authFacade.logout(authentication.getPrincipal());
  }
}
