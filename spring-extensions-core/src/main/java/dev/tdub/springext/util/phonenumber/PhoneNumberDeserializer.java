package dev.tdub.springext.util.phonenumber;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.tdub.springext.util.email.Email;
import dev.tdub.springext.util.email.EmailDto;

import java.io.IOException;

public class PhoneNumberDeserializer extends JsonDeserializer<PhoneNumber> {
  @Override
  public PhoneNumber deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    return new PhoneNumberDto(p.getValueAsString());
  }
}
