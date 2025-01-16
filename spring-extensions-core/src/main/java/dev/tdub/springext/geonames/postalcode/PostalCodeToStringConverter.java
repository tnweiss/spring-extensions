package dev.tdub.springext.geonames.postalcode;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class PostalCodeToStringConverter implements Converter<PostalCode, String> {
  @Override
  public String convert(PostalCode source) {
    return source.getCode();
  }
}
