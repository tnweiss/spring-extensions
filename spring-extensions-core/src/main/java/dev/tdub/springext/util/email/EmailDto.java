package dev.tdub.springext.util.email;

import com.fasterxml.jackson.annotation.JsonCreator;

import dev.tdub.springext.util.email.Email;
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
  public String toString() {
    return this.address;
  }
}
