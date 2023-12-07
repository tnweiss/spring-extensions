package dev.tdub.springext.auth.jwt;

public interface AuthResponse {
  String getAccessToken();
  String getRefreshToken();
}
