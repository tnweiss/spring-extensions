package dev.tdub.springext.auth.jwt;

public interface JwtAuthResponse {
  String getAccessToken();
  String getRefreshToken();
}
