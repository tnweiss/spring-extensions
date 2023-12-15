package dev.tdub.springext.geonames.postalcode;

import java.util.Optional;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class PostalCodeAttributeConverter implements AttributeConverter<PostalCode, String> {
  @Override
  public String convertToDatabaseColumn(PostalCode attribute) {
    return Optional.ofNullable(attribute)
        .map(PostalCode::getCode)
        .orElse(null);
  }

  @Override
  public PostalCode convertToEntityAttribute(String dbData) {
    return PostalCodeDto.fromPostalCode(dbData);
  }
}
