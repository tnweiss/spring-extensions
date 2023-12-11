package dev.tdub.springext.util;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Email {
  boolean isValid();

  @JsonValue
  String getAddress();
}
