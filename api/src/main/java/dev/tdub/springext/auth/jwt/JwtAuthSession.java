package dev.tdub.springext.auth;

import java.net.InetAddress;
import java.time.Instant;
import java.util.UUID;

public interface Session {
  UUID getSessionId();
  UUID getRefreshTokenId();
  Long getUserId();
  InetAddress getIp();
  String getCountryIsoCode();
  String getStateIsoCode();
  String getCity();
  Instant getLastUpdatedAt();
  Instant getCreatedAt();
}
