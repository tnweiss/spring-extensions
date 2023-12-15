package dev.tdub.springext.geonames.country;

import com.fasterxml.jackson.annotation.JsonValue;
import dev.tdub.springext.geonames.subdivision.Subdivision;

import java.util.Set;

public interface Country {
  String getName();

  @JsonValue()
  String getAlpha2Code();

  Set<Subdivision> getSubdivisions();
}
