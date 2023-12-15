package dev.tdub.springext.util.serdes;

import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.tdub.springext.geonames.country.Country;
import dev.tdub.springext.geonames.country.CountryDeserializer;
import dev.tdub.springext.geonames.country.CountrySerializer;
import dev.tdub.springext.geonames.postalcode.PostalCode;
import dev.tdub.springext.geonames.postalcode.PostalCodeDeserializer;
import dev.tdub.springext.geonames.postalcode.PostalCodeSerializer;
import dev.tdub.springext.geonames.subdivision.Subdivision;
import dev.tdub.springext.geonames.subdivision.SubdivisionDeserializer;
import dev.tdub.springext.geonames.subdivision.SubdivisionSerializer;
import dev.tdub.springext.util.email.Email;
import dev.tdub.springext.util.email.EmailDeserializer;
import dev.tdub.springext.util.email.EmailSerializer;
import dev.tdub.springext.util.phonenumber.PhoneNumber;
import dev.tdub.springext.util.phonenumber.PhoneNumberDeserializer;
import dev.tdub.springext.util.phonenumber.PhoneNumberSerializer;

public class UtilJacksonModule extends SimpleModule {
  public UtilJacksonModule() {
    super();

    addSerializer(new EmailSerializer());
    addDeserializer(Email.class, new EmailDeserializer());

    addSerializer(new PhoneNumberSerializer());
    addDeserializer(PhoneNumber.class, new PhoneNumberDeserializer());
  }
}
