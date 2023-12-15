package dev.tdub.springext.util.phonenumber;

import dev.tdub.springext.util.email.EmailDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;

@ReadingConverter
public class StringToPhoneNumberConverter implements Converter<String, PhoneNumber> {
  @Override
  public PhoneNumber convert(@NonNull String source) {
    return new PhoneNumberDto(source);
  }
}
