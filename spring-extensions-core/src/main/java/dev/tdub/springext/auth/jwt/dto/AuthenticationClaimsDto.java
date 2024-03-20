package dev.tdub.springext.auth.jwt.dto;

import java.util.Map;

import dev.tdub.springext.auth.jwt.AuthenticationClaims;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class AuthenticationClaimsDto implements AuthenticationClaims {
  private final Long sub;
  private final Map<String, Object> additionalClaims;
}
