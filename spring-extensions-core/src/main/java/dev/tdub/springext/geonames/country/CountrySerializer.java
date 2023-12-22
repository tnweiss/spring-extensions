package dev.tdub.springext.geonames.country;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CountrySerializer extends StdSerializer<Country> {
  public static final CountrySerializer INSTANCE = new CountrySerializer();

  protected CountrySerializer() {
    super(Country.class);
  }

  @Override
  public void serialize(Country value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getAlpha2Code());
  }
}
