package dev.tdub.springext.auth.service;

import dev.tdub.springext.auth.Network;

import java.util.Optional;

public interface NetworkAuthService {
  Optional<Network> get(String ip);
}
