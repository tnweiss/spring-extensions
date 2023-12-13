package dev.tdub.springext.util;

import java.util.Optional;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmailAttributeConverter implements AttributeConverter<Email, String> {

  @Override
  public String convertToDatabaseColumn(Email email) {
    return Optional.ofNullable(email)
        .map(Email::getAddress)
        .orElse(null);
  }

  @Override
  public EmailDto convertToEntityAttribute(String dbData) {
    return new EmailDto(dbData);
  }
}
