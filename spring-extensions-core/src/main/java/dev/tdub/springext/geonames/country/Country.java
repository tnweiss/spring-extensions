package dev.tdub.springext.geonames.country;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dev.tdub.springext.geonames.subdivision.Subdivision;

import java.util.Set;

@JsonSerialize(using = CountrySerializer.class)
@JsonDeserialize(using = CountryDeserializer.class)
public interface Country {
  String getName();

  @JsonValue()
  String getAlpha2Code();

  Set<Subdivision> getSubdivisions();
}
