package dev.tdub.springext.auth.jwt;

public interface BasicAuthRequest {
  String getUsername();
  String getPassword();
}
