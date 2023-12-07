package com.ora.web.common.dto.network;

import java.net.InetAddress;

public interface Network {
  InetAddress getNetwork();
  String getCountryIsoCode();
  String getStateIsoCode();
  String getCity();
}
