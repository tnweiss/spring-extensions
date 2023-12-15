package dev.tdub.springext.geonames.postalcode;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

@ReadingConverter
public class StringToPostalCodeConverter implements Converter<String, PostalCode> {
  @Override
  public PostalCode convert(@NonNull String source) {
    return PostalCodeDto.fromPostalCode(source);
  }
}
