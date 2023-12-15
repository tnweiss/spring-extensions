package dev.tdub.springext.geonames.postalcode;

import com.fasterxml.jackson.annotation.JsonValue;
import dev.tdub.springext.geonames.subdivision.Subdivision;
import dev.tdub.springext.geonames.country.Country;

public interface PostalCode {
  @JsonValue()
  String getCode();

  Subdivision getSubdivision();

  Country getCountry();
}
