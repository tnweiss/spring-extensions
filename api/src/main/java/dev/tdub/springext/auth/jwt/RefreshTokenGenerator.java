package com.ora.web.common.security.jwt;

import com.ora.web.common.dto.auth.RefreshTokenClaimsDto;

public interface RefreshTokenGenerator {
  String create(RefreshTokenClaimsDto claims);
}
