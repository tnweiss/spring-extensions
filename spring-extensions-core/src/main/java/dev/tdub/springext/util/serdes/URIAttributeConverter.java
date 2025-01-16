package dev.tdub.springext.util.serdes;

import java.net.URI;

import jakarta.persistence.AttributeConverter;

public class URIAttributeConverter implements AttributeConverter<URI, String> {
  @Override
  public String convertToDatabaseColumn(URI attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.toString();
  }

  @Override
  public URI convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return URI.create(dbData);
  }
}
