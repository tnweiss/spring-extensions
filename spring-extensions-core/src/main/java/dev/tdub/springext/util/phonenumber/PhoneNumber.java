package dev.tdub.springext.util.phonenumber;

import com.fasterxml.jackson.annotation.JsonValue;

public interface PhoneNumber {
  boolean isValid();

  @JsonValue
  String getFormattedNumber();
}
