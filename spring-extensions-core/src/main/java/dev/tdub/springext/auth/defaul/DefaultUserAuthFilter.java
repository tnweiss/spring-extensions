package dev.tdub.springext.auth.defaul;

import dev.tdub.springext.auth.Authentication;
import dev.tdub.springext.auth.Authenticator;
import dev.tdub.springext.auth.UserPrincipal;
import dev.tdub.springext.error.exceptions.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1000)
@ConditionalOnProperty(
    value = "springext.auth.authenticators.default.enabled",
    havingValue = "true"
)
@Component
@RequiredArgsConstructor
public class DefaultUserAuthFilter implements Authenticator {
  private final DefaultUserPrincipalSupplier defaultUserPrincipalSupplier;

  @Override
  public boolean canAuthenticate(HttpServletRequest request) {
    return true;
  }

  @Override
  public Authentication authenticate(HttpServletRequest request) throws AuthenticationException {
    UserPrincipal principal = defaultUserPrincipalSupplier.get();
    return new DefaultUserAuthenticationDto(principal);
  }
}
