package dev.tdub.springext.email.serdes;

import java.util.List;

import dev.tdub.springext.util.phonenumber.PhoneNumberToStringConverter;
import dev.tdub.springext.util.phonenumber.StringToPhoneNumberConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

public class EmailMongoCustomConversions extends MongoCustomConversions {

  public EmailMongoCustomConversions() {
    super(List.of(
        new StringToEmailConverter(),
        new EmailToStringConverter()
    ));
  }
}
