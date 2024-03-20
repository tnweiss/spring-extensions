package dev.tdub.springext.geonames.country;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CountryDeserializer extends StdDeserializer<Country> {
  public static final CountryDeserializer INSTANCE = new CountryDeserializer();

  protected CountryDeserializer() {
    super(Country.class);
  }

  @Override
  public Country deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    return CountryDto.fromAlpha2Code(p.getValueAsString());
  }
}
