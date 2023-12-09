package dev.tdub.springext.util;

import java.util.Optional;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class EmailAttributeConverter implements AttributeConverter<Email, String> {
  @Override
  public String convertToDatabaseColumn(Email attribute) {
    return Optional.ofNullable(attribute)
        .map(Email::getAddress)
        .orElse(null);
  }

  @Override
  public Email convertToEntityAttribute(String dbData) {
    return new Email(dbData);
  }
}
