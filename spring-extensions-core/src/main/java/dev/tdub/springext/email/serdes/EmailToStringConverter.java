package dev.tdub.springext.email.serdes;

import dev.tdub.springext.email.Email;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class EmailToStringConverter implements Converter<Email, String> {
  @Override
  public String convert(Email source) {
    return source.getAddress();
  }
}
