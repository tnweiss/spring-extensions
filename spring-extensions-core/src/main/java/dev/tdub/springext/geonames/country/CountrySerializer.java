package dev.tdub.springext.geonames.country;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CountrySerializer extends JsonSerializer<Country> {
  @Override
  public void serialize(Country value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getAlpha2Code());
  }
}
