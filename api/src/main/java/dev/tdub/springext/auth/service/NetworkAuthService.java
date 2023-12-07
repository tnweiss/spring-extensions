package dev.tdub.springext.auth.service;

import java.util.Optional;

import dev.tdub.springext.auth.Network;

public interface NetworkAuthService {
  Optional<Network> get(String ip);
}
