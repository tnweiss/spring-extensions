package dev.tdub.springext.geonames;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Country {
  String getName();

  @JsonValue()
  String getAlpha2Code();

  Set<Subdivision> getSubdivisions();
}
