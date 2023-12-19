package dev.tdub.springext.email.serdes;

import com.fasterxml.jackson.databind.module.SimpleModule;

import dev.tdub.springext.email.Email;
import dev.tdub.springext.util.phonenumber.PhoneNumber;
import dev.tdub.springext.util.phonenumber.PhoneNumberDeserializer;
import dev.tdub.springext.util.phonenumber.PhoneNumberSerializer;

public class EmailJacksonModule extends SimpleModule {
  public EmailJacksonModule() {
    super();

    addSerializer(new EmailSerializer());
    addDeserializer(Email.class, new EmailDeserializer());
  }
}
