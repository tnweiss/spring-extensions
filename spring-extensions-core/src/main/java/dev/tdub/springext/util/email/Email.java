package dev.tdub.springext.util.email;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Email {
  boolean isValid();

  @JsonValue
  String getAddress();
}
