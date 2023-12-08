package dev.tdub.springext.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class Email {
  private String address;

  @JsonCreator
  public Email(String address) {
    setAddress(address);
  }

  public void setAddress(String address) {
    requireValid(address);
    this.address = address;
  }

  private static void requireValid(String address) {
    if(address == null || !address.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
      throw new IllegalArgumentException("Invalid email address: '" + address + "'");
    }
  }

  @JsonValue
  public String getAddress() {
    return address;
  }
}
