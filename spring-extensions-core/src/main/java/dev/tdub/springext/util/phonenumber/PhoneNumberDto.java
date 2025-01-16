package dev.tdub.springext.util.phonenumber;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import dev.tdub.springext.error.exceptions.ClientException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PhoneNumberDto implements PhoneNumber {
  private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();

  private final Phonenumber.PhoneNumber number;

  @JsonCreator
  public PhoneNumberDto(String number) {
    try {
      this.number = PHONE_NUMBER_UTIL.parse(number, "US");
    } catch (Exception e) {
      throw new ClientException("Unable to parse phone number: '%s'".formatted(number));
    }
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public String getFormattedNumber() {
    return PHONE_NUMBER_UTIL.format(this.number, PhoneNumberUtil.PhoneNumberFormat.E164);
  }
}
