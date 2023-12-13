package dev.tdub.springext.geonames.postalcode;

import dev.tdub.springext.geonames.PostalCode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;

@WritingConverter
public class PostalCodeToStringConverter implements Converter<PostalCode, String> {
  @Override
  public String convert(PostalCode source) {
    return source.getCode();
  }
}
