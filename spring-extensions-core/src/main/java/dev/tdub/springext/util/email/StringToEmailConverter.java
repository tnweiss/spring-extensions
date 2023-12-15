package dev.tdub.springext.util.email;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

@ReadingConverter
public class StringToEmailConverter implements Converter<String, Email> {
  @Override
  public Email convert(@NonNull String source) {
    return new EmailDto(source);
  }
}
