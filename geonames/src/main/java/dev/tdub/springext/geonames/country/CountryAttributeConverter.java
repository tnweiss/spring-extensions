package dev.tdub.springext.geonames.country;

import java.util.Optional;

import dev.tdub.springext.geonames.Country;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter
public class CountryAttributeConverter implements AttributeConverter<Country, String> {
  @Override
  public String convertToDatabaseColumn(Country attribute) {
    return Optional.ofNullable(attribute)
        .map(Country::getAlpha2Code)
        .orElse(null);
  }

  @Override
  public Country convertToEntityAttribute(String dbData) {
    return CountryDto.fromAlpha2Code(dbData);
  }
}
