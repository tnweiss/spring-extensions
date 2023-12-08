package dev.tdub.springext.geonames;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Subdivision {
  String getName();

  @JsonValue()
  String getAlpha2Code();

  Country getCountry();

  Set<PostalCode> getPostalCodes();
}
