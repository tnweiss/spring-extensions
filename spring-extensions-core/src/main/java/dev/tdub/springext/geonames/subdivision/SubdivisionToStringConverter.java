package dev.tdub.springext.geonames.subdivision;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class SubdivisionToStringConverter implements Converter<Subdivision, String> {
  @Override
  public String convert(Subdivision source) {
    return source.getAlpha2Code();
  }
}
