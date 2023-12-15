package dev.tdub.springext.auth.service;

public interface UserAuthService {
  Long authenticate(String username, String password);
  void requireActiveUser(Long userId);
}
