package dev.tdub.springext.geonames.postalcode;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PostalCodeSerializer extends StdSerializer<PostalCode> {
  public static final PostalCodeSerializer INSTANCE = new PostalCodeSerializer();

  protected PostalCodeSerializer() {
    super(PostalCode.class);
  }

  @Override
  public void serialize(PostalCode value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getCode());
  }
}
