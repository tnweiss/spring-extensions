package dev.tdub.springext.geonames.postalcode;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dev.tdub.springext.geonames.subdivision.Subdivision;
import dev.tdub.springext.geonames.country.Country;

@JsonSerialize(using = PostalCodeSerializer.class)
@JsonDeserialize(using = PostalCodeDeserializer.class)
public interface PostalCode {
  @JsonValue()
  String getCode();

  Subdivision getSubdivision();

  Country getCountry();
}
