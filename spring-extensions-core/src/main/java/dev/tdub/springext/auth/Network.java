package dev.tdub.springext.auth;

import dev.tdub.springext.geonames.country.Country;
import dev.tdub.springext.geonames.subdivision.Subdivision;

import java.net.InetAddress;

public interface Network {
  InetAddress getNetwork();
  Country getCountry();
  Subdivision getState();
  String getCity();
}
