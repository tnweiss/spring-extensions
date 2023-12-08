package dev.tdub.springext.geonames;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PostalCodeAttributeConverter implements AttributeConverter<PostalCode, String> {
  @Override
  public String convertToDatabaseColumn(PostalCode attribute) {
    return attribute.getCode();
  }

  @Override
  public PostalCode convertToEntityAttribute(String dbData) {
    return Geonames.getPostalCode(dbData);
  }
}
