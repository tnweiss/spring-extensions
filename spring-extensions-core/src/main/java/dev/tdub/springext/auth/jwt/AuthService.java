package dev.tdub.springext.auth.jwt;

import dev.tdub.springext.auth.jwt.dto.AccessTokenClaimsDto;
import dev.tdub.springext.auth.jwt.dto.AuthResponseDto;
import dev.tdub.springext.auth.jwt.dto.RefreshTokenClaimsDto;
import dev.tdub.springext.error.exceptions.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
    value = "springext.auth.authenticators.jwt.enabled",
    havingValue = "true"
)
@Component
@Log4j2
@RequiredArgsConstructor
public class AuthService {
  private final AccessTokenGenerator accessTokenGenerator;
  private final RefreshTokenGenerator refreshTokenGenerator;
  private final JwtParser parser;

  public JwtAuthResponse createTokens(JwtAuthSession jwtAuthSession) {
    String accessToken = accessTokenGenerator.create(new AccessTokenClaimsDto(jwtAuthSession.getUserId(), jwtAuthSession.getSessionId()));
    String refreshToken = refreshTokenGenerator.create(new RefreshTokenClaimsDto(jwtAuthSession.getUserId(), jwtAuthSession.getSessionId(),
        jwtAuthSession.getRefreshTokenId()));
    return new AuthResponseDto(accessToken, refreshToken);
  }

  public AccessTokenClaims verifyAccessToken(String accessToken) {
    try {
      Claims claims = parser.parseSignedClaims(accessToken).getPayload();
      return new AccessTokenClaimsDto(claims);
    } catch (JwtException ex) {
      log.debug(ex);
      throw new AuthenticationException();
    }
  }

  public RefreshTokenClaims verifyRefreshToken(String refreshToken) {
    try {
      Claims claims = parser.parseSignedClaims(refreshToken).getPayload();
      return new RefreshTokenClaimsDto(claims);
    } catch (JwtException ex) {
      log.debug(ex);
      throw new AuthenticationException();
    }
  }
}
