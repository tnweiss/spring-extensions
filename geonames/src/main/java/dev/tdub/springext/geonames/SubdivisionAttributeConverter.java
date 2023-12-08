package dev.tdub.springext.geonames;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SubdivisionAttributeConverter implements AttributeConverter<Subdivision, String> {
  @Override
  public String convertToDatabaseColumn(Subdivision attribute) {
    return attribute.getAlpha2Code();
  }

  @Override
  public Subdivision convertToEntityAttribute(String dbData) {
    return Geonames.getSubdivision(dbData);
  }
}
