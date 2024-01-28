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
  private Long sub;
  private Map<String, Object> additionalClaims;
}
