package com.ora.web.common.security.jwt;

import com.ora.web.common.dto.auth.AccessTokenClaimsDto;

public interface AccessTokenGenerator {
  String create(AccessTokenClaimsDto claims);
}
