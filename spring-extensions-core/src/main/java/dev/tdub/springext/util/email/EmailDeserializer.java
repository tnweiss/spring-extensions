package dev.tdub.springext.util.email;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EmailDeserializer extends JsonDeserializer<Email> {
  @Override
  public Email deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    return new EmailDto(p.getValueAsString());
  }
}
