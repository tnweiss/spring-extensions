package dev.tdub.springext.email.serdes;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import dev.tdub.springext.email.Email;
import dev.tdub.springext.email.EmailDto;

public class EmailDeserializer extends StdDeserializer<Email> {
  public static final EmailDeserializer INSTANCE = new EmailDeserializer();

  protected EmailDeserializer() {
    super(Email.class);
  }

  @Override
  public Email deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    return new EmailDto(p.getValueAsString());
  }
}
