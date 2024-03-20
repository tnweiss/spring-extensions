package dev.tdub.springext.geonames.subdivision;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class SubdivisionDeserializer extends StdDeserializer<Subdivision> {
  public static final SubdivisionDeserializer INSTANCE = new SubdivisionDeserializer();

  protected SubdivisionDeserializer() {
    super(Subdivision.class);
  }

  @Override
  public Subdivision deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    return SubdivisionDto.fromAlpha2Code(p.getValueAsString());
  }
}
