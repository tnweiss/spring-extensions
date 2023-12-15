package dev.tdub.springext.geonames.subdivision;

import com.fasterxml.jackson.annotation.JsonValue;
import dev.tdub.springext.geonames.country.Country;
import dev.tdub.springext.geonames.postalcode.PostalCode;

import java.util.Set;

public interface Subdivision {
  String getName();

  @JsonValue()
  String getAlpha2Code();

  Country getCountry();

  Set<PostalCode> getPostalCodes();
}
