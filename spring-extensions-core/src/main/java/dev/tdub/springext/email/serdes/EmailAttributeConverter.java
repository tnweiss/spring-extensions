package dev.tdub.springext.email.serdes;

import java.util.Optional;

import dev.tdub.springext.email.Email;
import dev.tdub.springext.email.EmailDto;
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
  public Email convertToEntityAttribute(String dbData) {
    return new EmailDto(dbData);
  }
}
