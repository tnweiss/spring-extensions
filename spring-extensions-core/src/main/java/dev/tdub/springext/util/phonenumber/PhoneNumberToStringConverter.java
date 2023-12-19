package dev.tdub.springext.util.phonenumber;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class PhoneNumberToStringConverter implements Converter<PhoneNumber, String> {
  @Override
  public String convert(PhoneNumber source) {
    return source.getFormattedNumber();
  }
}
