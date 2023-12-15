package dev.tdub.springext.util.email;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class EmailToStringConverter implements Converter<Email, String> {
  @Override
  public String convert(Email source) {
    return source.getAddress();
  }
}
