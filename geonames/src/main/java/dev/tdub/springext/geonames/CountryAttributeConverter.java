package dev.tdub.springext.geonames;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CountryAttributeConverter implements AttributeConverter<Country, String> {
  @Override
  public String convertToDatabaseColumn(Country attribute) {
    return attribute.getAlpha2Code();
  }

  @Override
  public Country convertToEntityAttribute(String dbData) {
    return Geonames.getCountry(dbData);
  }
}
