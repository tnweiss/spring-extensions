package dev.tdub.springext.util.phonenumber;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Converter(autoApply = true)
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
