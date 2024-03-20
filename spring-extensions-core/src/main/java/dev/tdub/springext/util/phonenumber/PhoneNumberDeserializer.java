package dev.tdub.springext.util.phonenumber;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class PhoneNumberDeserializer extends StdDeserializer<PhoneNumber> {
  public static final PhoneNumberDeserializer INSTANCE = new PhoneNumberDeserializer();

  protected PhoneNumberDeserializer() {
    super(PhoneNumber.class);
  }

  @Override
  public PhoneNumber deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    return new PhoneNumberDto(p.getValueAsString());
  }
}
