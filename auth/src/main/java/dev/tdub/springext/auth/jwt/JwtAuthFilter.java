package dev.tdub.springext.auth.authenticator;

import com.ora.web.common.dto.auth.OraAccessTokenAuthenticationDto;
import com.ora.web.common.security.OraAuthenticator;
import com.ora.web.service.AuthService;

import dev.tdub.springext.auth.Authentication;
import dev.tdub.springext.auth.jwt.AccessTokenClaims;
import dev.tdub.springext.error.exceptions.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(0)
@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements OraAuthenticator {
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
    return new OraAccessTokenAuthenticationDto(claims, accessToken);
  }
}
