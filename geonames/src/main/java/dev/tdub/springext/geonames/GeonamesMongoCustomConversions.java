package dev.tdub.springext.geonames;

import java.util.List;

import dev.tdub.springext.geonames.postalcode.PostalCodeToStringConverter;
import dev.tdub.springext.geonames.postalcode.StringToPostalCodeConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

public class GeonamesMongoCustomConversions extends MongoCustomConversions {

  public GeonamesMongoCustomConversions() {
    super(List.of(
        new StringToPostalCodeConverter(),
        new PostalCodeToStringConverter()
    ));
  }
}
