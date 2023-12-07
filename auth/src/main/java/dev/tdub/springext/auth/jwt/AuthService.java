package dev.tdub.springext.auth;

import com.ora.web.common.dto.auth.AccessTokenClaimsDto;
import com.ora.web.common.dto.auth.AuthResponseDto;
import com.ora.web.common.dto.auth.RefreshTokenClaimsDto;
import com.ora.web.common.dto.session.Session;
import com.ora.web.common.security.jwt.AccessTokenGenerator;
import com.ora.web.common.security.jwt.RefreshTokenGenerator;

import dev.tdub.springext.error.exceptions.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {
  private final AccessTokenGenerator accessTokenGenerator;
  private final RefreshTokenGenerator refreshTokenGenerator;
  private final JwtParser parser;

  public AuthResponseDto createTokens(Session session) {
    String accessToken = accessTokenGenerator.create(new AccessTokenClaimsDto(session.getUserId(), session.getSessionId()));
    String refreshToken = refreshTokenGenerator.create(new RefreshTokenClaimsDto(session.getUserId(), session.getSessionId(),
        session.getRefreshTokenId()));
    return new AuthResponseDto(accessToken, refreshToken);
  }

  public AccessTokenClaimsDto verifyAccessToken(String accessToken) {
    try {
      Claims claims = parser.parseSignedClaims(accessToken).getPayload();
      return new AccessTokenClaimsDto(claims);
    } catch (JwtException ex) {
      throw new AuthenticationException();
    }
  }

  public RefreshTokenClaimsDto verifyRefreshToken(String refreshToken) {
    try {
      Claims claims = parser.parseSignedClaims(refreshToken).getPayload();
      return new RefreshTokenClaimsDto(claims);
    } catch (JwtException ex) {
      throw new AuthenticationException();
    }
  }
}
