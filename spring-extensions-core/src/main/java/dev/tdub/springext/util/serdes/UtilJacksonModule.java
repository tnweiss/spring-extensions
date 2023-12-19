package dev.tdub.springext.util.serdes;

import com.fasterxml.jackson.databind.module.SimpleModule;

import dev.tdub.springext.util.phonenumber.PhoneNumber;
import dev.tdub.springext.util.phonenumber.PhoneNumberDeserializer;
import dev.tdub.springext.util.phonenumber.PhoneNumberSerializer;

public class UtilJacksonModule extends SimpleModule {
  public UtilJacksonModule() {
    super();

    addSerializer(new PhoneNumberSerializer());
    addDeserializer(PhoneNumber.class, new PhoneNumberDeserializer());
  }
}
