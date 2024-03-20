package dev.tdub.springext.util.phonenumber;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PhoneNumberSerializer extends StdSerializer<PhoneNumber> {
  public static final PhoneNumberSerializer INSTANCE = new PhoneNumberSerializer();

  protected PhoneNumberSerializer() {
    super(PhoneNumber.class);
  }

  @Override
  public void serialize(PhoneNumber value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getFormattedNumber());
  }
}
