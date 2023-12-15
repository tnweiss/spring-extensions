package dev.tdub.springext.util.serdes;

import dev.tdub.springext.util.email.EmailToStringConverter;
import dev.tdub.springext.util.email.StringToEmailConverter;
import dev.tdub.springext.util.phonenumber.PhoneNumberToStringConverter;
import dev.tdub.springext.util.phonenumber.StringToPhoneNumberConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

public class UtilMongoCustomConversions extends MongoCustomConversions {

  public UtilMongoCustomConversions() {
    super(List.of(
        new StringToEmailConverter(),
        new EmailToStringConverter(),

        new StringToPhoneNumberConverter(),
        new PhoneNumberToStringConverter()
    ));
  }
}
