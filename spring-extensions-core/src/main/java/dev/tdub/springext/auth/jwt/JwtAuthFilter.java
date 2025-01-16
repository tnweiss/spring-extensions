package dev.tdub.springext.auth.jwt;

import dev.tdub.springext.auth.Authentication;
import dev.tdub.springext.auth.Authenticator;
import dev.tdub.springext.auth.jwt.dto.AccessTokenAuthenticationDto;
import dev.tdub.springext.error.exceptions.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(0)
@ConditionalOnProperty(
    value = "springext.auth.authenticators.jwt.enabled",
    havingValue = "true"
)
@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements Authenticator {
  public static final String AUTH_HEADER = "Authorization";

  private final AuthService authService;

  @Override
  public boolean canAuthenticate(HttpServletRequest request) {
    String auth = request.getHeader(AUTH_HEADER);
    return auth != null && auth.startsWith("Bearer ");
  }

  @Override
  public Authentication authenticate(HttpServletRequest request) throws AuthenticationException {
    String accessToken = request.getHeader(AUTH_HEADER)
        .replace("Bearer ", "");
    AccessTokenClaims claims = authService.verifyAccessToken(accessToken);
    return new AccessTokenAuthenticationDto(claims, accessToken);
  }
}
