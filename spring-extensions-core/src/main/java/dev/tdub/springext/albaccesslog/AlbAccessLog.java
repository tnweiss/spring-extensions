package dev.tdub.springext.albaccesslog;

import java.net.InetAddress;
import java.time.Instant;

public interface AlbAccessLog {
  Instant getTime();
  InetAddress getClientIp();
  String getRequestMethod();
  String getRequestUrl();
  Integer getRequestProcessingTime();
  Integer getTargetProcessingTime();
  Integer getResponseProcessingTime();
  Integer getTargetStatusCode();
  String getFullLog();
}
