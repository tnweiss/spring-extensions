package dev.tdub.springext.util.phonenumber;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = PhoneNumberSerializer.class)
@JsonDeserialize(using = PhoneNumberDeserializer.class)
public interface PhoneNumber {
  boolean isValid();

  @JsonValue
  String getFormattedNumber();
}
