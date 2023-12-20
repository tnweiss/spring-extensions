package dev.tdub.springext.email.serdes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import dev.tdub.springext.email.Email;

public class EmailSerializer extends StdSerializer<Email> {
  public static final EmailSerializer INSTANCE = new EmailSerializer();

  protected EmailSerializer() {
    super(Email.class);
  }

  @Override
  public void serialize(Email value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getAddress());
  }
}
