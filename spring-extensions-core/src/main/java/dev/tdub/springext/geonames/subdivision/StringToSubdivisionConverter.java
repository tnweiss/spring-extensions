package dev.tdub.springext.geonames.subdivision;

import dev.tdub.springext.geonames.country.Country;
import dev.tdub.springext.geonames.country.CountryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

@ReadingConverter
public class StringToSubdivisionConverter implements Converter<String, Country> {
  @Override
  public Country convert(@NonNull String source) {
    return CountryDto.fromAlpha2Code(source);
  }
}
