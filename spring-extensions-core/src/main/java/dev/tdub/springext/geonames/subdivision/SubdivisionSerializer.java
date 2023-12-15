package dev.tdub.springext.geonames.subdivision;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SubdivisionSerializer extends JsonSerializer<Subdivision> {
  @Override
  public void serialize(Subdivision value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getAlpha2Code());
  }
}
