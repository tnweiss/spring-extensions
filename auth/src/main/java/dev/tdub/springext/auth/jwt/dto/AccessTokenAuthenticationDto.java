package dev.tdub.springext.auth.jwt.dto;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.ora.web.common.dto.user.UserPrincipalDto;

import dev.tdub.springext.auth.Authentication;
import dev.tdub.springext.auth.UserPrincipal;
import dev.tdub.springext.auth.jwt.AccessTokenClaims;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode
@RequiredArgsConstructor
public class OraAccessTokenAuthenticationDto implements Authentication {
  private final UserPrincipal principal;
  private final String accessToken;
  private final AccessTokenClaims accessTokenClaims;

  public OraAccessTokenAuthenticationDto(AccessTokenClaims claims, String accessToken) {
    this.principal = new UserPrincipalDto(claims.getSub(), claims.getSessionId());
    this.accessToken = accessToken;
    this.accessTokenClaims = claims;
  }

  public Long callerId() {
    return accessTokenClaims.getSub();
  }

  public UUID sessionId() {
    return accessTokenClaims.getSessionId();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getCredentials() {
    return accessToken;
  }

  @Override
  public AccessTokenClaims getDetails() {
    return accessTokenClaims;
  }

  @Override
  public UserPrincipal getPrincipal() {
    return principal;
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
