package dev.tdub.springext.geonames.country;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

@ReadingConverter
public class StringToCountryConverter implements Converter<String, Country> {
  @Override
  public Country convert(@NonNull String source) {
    return CountryDto.fromAlpha2Code(source);
  }
}
