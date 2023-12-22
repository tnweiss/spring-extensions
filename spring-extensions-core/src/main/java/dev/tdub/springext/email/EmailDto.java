package dev.tdub.springext.email;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.mail.internet.InternetAddress;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class EmailDto implements Email {
  private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

  private final String address;

  @JsonCreator
  public EmailDto(String address) {
    if (address == null) {
      throw new IllegalArgumentException("Email address cannot be null");
    }
    this.address = address.toLowerCase();
  }

  @Override
  public boolean isValid() {
    return address.matches(EMAIL_REGEX);
  }

  @Override
  public InternetAddress getInternetAddress() {
    try {
      return new InternetAddress(address);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid email address: " + address);
    }
  }

  @Override
  public String toString() {
    return this.address;
  }
}