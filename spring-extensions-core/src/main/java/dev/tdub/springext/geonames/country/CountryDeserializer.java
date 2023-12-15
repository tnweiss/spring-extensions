package dev.tdub.springext.geonames.country;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CountryDeserializer extends JsonDeserializer<Country> {
  @Override
  public Country deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    return CountryDto.fromAlpha2Code(p.getValueAsString());
  }
}
