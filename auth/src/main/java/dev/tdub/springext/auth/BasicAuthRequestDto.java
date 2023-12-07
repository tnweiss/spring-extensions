package dev.tdub.springext.auth.dto;

import dev.tdub.springext.auth.BasicAuthRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@ToString
@Jacksonized
@EqualsAndHashCode
@RequiredArgsConstructor
public class BasicAuthRequestDto implements BasicAuthRequest {
  private final String username;
  private final String password;
}
