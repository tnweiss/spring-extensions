package dev.tdub.springext.geonames.serdes;

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

public class GeonamesJacksonModule extends SimpleModule {
  public GeonamesJacksonModule() {
    super();

    addSerializer(new PostalCodeSerializer());
    addDeserializer(PostalCode.class, new PostalCodeDeserializer());

    addSerializer(new SubdivisionSerializer());
    addDeserializer(Subdivision.class, new SubdivisionDeserializer());

    addSerializer(new CountrySerializer());
    addDeserializer(Country.class, new CountryDeserializer());
  }
}
