package dev.tdub.springext.geonames.postalcode;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class PostalCodeDeserializer extends StdDeserializer<PostalCode> {
  public static final PostalCodeDeserializer INSTANCE = new PostalCodeDeserializer();

  protected PostalCodeDeserializer() {
    super(PostalCode.class);
  }

  @Override
  public PostalCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    return PostalCodeDto.fromPostalCode(p.getValueAsString());
  }
}
