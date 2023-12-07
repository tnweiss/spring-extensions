package dev.tdub.springext.auth;

import java.net.InetAddress;

public interface Network {
  InetAddress getNetwork();
  String getCountryIsoCode();
  String getStateIsoCode();
  String getCity();
}
