package dev.tdub.springext.util.phonenumber;

import dev.tdub.springext.util.email.Email;
import dev.tdub.springext.util.email.EmailDto;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter
public class PhoneNumberAttributeConverter implements AttributeConverter<PhoneNumber, String> {

  @Override
  public String convertToDatabaseColumn(PhoneNumber phoneNumber) {
    return Optional.ofNullable(phoneNumber)
        .map(PhoneNumber::getFormattedNumber)
        .orElse(null);
  }

  @Override
  public PhoneNumber convertToEntityAttribute(String dbData) {
    return new PhoneNumberDto(dbData);
  }
}
