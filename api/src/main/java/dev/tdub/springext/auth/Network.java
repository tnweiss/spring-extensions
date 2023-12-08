package dev.tdub.springext.auth;

import java.net.InetAddress;

import dev.tdub.springext.geonames.Country;
import dev.tdub.springext.geonames.Subdivision;

public interface Network {
  InetAddress getNetwork();
  Country getCountry();
  Subdivision getState();
  String getCity();
}
