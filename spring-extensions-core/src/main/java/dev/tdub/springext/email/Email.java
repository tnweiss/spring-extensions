package dev.tdub.springext.email;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dev.tdub.springext.email.serdes.EmailDeserializer;
import dev.tdub.springext.email.serdes.EmailSerializer;
import jakarta.mail.internet.InternetAddress;

@JsonSerialize(using = EmailSerializer.class)
@JsonDeserialize(using = EmailDeserializer.class)
public interface Email {
  boolean isValid();

  @JsonValue
  String getAddress();

  InternetAddress getInternetAddress();
}
