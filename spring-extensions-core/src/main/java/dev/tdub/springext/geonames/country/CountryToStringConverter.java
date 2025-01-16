package dev.tdub.springext.geonames.country;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class CountryToStringConverter implements Converter<Country, String> {
  @Override
  public String convert(Country source) {
    return source.getAlpha2Code();
  }
}
