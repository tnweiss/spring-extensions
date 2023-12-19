package dev.tdub.springext.email;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.mail.internet.InternetAddress;

public interface Email {
  boolean isValid();

  @JsonValue
  String getAddress();

  InternetAddress getInternetAddress();
}
