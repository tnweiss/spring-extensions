package dev.tdub.springext.geonames.subdivision;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dev.tdub.springext.geonames.country.Country;
import dev.tdub.springext.geonames.postalcode.PostalCode;

import java.util.Set;

@JsonSerialize(using = SubdivisionSerializer.class)
@JsonDeserialize(using = SubdivisionDeserializer.class)
public interface Subdivision {
  String getName();

  @JsonValue()
  String getAlpha2Code();

  Country getCountry();

  Set<PostalCode> getPostalCodes();
}
