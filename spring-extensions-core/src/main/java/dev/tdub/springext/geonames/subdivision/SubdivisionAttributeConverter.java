package dev.tdub.springext.geonames.subdivision;

import java.util.Optional;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class SubdivisionAttributeConverter implements AttributeConverter<Subdivision, String> {
  @Override
  public String convertToDatabaseColumn(Subdivision attribute) {
    return Optional.ofNullable(attribute)
        .map(Subdivision::getAlpha2Code)
        .orElse(null);
  }

  @Override
  public Subdivision convertToEntityAttribute(String dbData) {
    return Optional.ofNullable(dbData)
        .map(SubdivisionDto::fromAlpha2Code)
        .orElse(null);
  }
}
