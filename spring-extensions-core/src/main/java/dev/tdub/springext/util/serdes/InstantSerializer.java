package dev.tdub.springext.util.serdes;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class InstantSerializer extends StdSerializer<Instant> {
  public static final InstantSerializer INSTANCE = new InstantSerializer();

  protected InstantSerializer() {
    super(Instant.class);
  }

  @Override
  public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(value.toString());
  }
}
