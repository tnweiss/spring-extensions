package dev.tdub.springext.util.serdes;

import jakarta.persistence.AttributeConverter;
import org.springframework.http.MediaType;

public class MediaTypeAttributeConverter implements AttributeConverter<MediaType, String> {

  @Override
  public String convertToDatabaseColumn(MediaType attribute) {
    if (attribute != null) {
      return attribute.toString();
    }
    return null;
  }

  @Override
  public MediaType convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return MediaType.parseMediaType(dbData);
  }
}
