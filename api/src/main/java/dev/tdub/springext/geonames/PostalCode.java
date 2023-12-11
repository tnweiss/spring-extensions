package dev.tdub.springext.geonames;

import com.fasterxml.jackson.annotation.JsonValue;

public interface PostalCode {
  @JsonValue()
  String getCode();

  Subdivision getSubdivision();

  Country getCountry();
}
