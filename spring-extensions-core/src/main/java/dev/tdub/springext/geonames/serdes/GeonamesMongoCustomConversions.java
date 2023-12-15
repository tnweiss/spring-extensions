package dev.tdub.springext.geonames.serdes;

import java.util.List;

import dev.tdub.springext.geonames.country.CountryToStringConverter;
import dev.tdub.springext.geonames.country.StringToCountryConverter;
import dev.tdub.springext.geonames.postalcode.PostalCodeToStringConverter;
import dev.tdub.springext.geonames.postalcode.StringToPostalCodeConverter;
import dev.tdub.springext.geonames.subdivision.StringToSubdivisionConverter;
import dev.tdub.springext.geonames.subdivision.SubdivisionToStringConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

public class GeonamesMongoCustomConversions extends MongoCustomConversions {

  public GeonamesMongoCustomConversions() {
    super(List.of(
        new StringToPostalCodeConverter(),
        new PostalCodeToStringConverter(),

        new StringToCountryConverter(),
        new CountryToStringConverter(),

        new StringToSubdivisionConverter(),
        new SubdivisionToStringConverter()
    ));
  }
}
