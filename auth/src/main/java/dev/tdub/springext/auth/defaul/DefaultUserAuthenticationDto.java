package dev.tdub.springext.auth.dto;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.ora.web.common.dto.user.User;
import com.ora.web.common.dto.user.UserPrincipalDto;

import dev.tdub.springext.auth.Authentication;
import dev.tdub.springext.auth.UserPrincipal;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode
@RequiredArgsConstructor
public class OraDefaultUserAuthenticationDto implements Authentication {
  private final UserPrincipal principal;

  public OraDefaultUserAuthenticationDto(User user) {
    this.principal = new UserPrincipalDto(user.getId(), null);
  }

  public Long callerId() {
    return principal.getUserId();
  }

  public UUID sessionId() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getCredentials() {
    return null;
  }

  @Override
  public AccessTokenClaimsDto getDetails() {
    return null;
  }

  @Override
  public UserPrincipal getPrincipal() {
    return this.principal;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

  }

  @Override
  public String getName() {
    return null;
  }
}
