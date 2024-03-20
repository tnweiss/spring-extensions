package dev.tdub.springext.geonames.subdivision;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SubdivisionSerializer extends StdSerializer<Subdivision> {
  public static final SubdivisionSerializer INSTANCE = new SubdivisionSerializer();

  protected SubdivisionSerializer() {
    super(Subdivision.class);
  }

  @Override
  public void serialize(Subdivision value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getAlpha2Code());
  }
}
