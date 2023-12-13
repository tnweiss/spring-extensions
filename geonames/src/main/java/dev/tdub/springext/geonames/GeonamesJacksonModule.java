package dev.tdub.springext.geonames;

import com.fasterxml.jackson.databind.module.SimpleModule;

import dev.tdub.springext.geonames.postalcode.PostalCodeDeserializer;
import dev.tdub.springext.geonames.postalcode.PostalCodeSerializer;

public class GeonamesJacksonModule extends SimpleModule {
  public GeonamesJacksonModule() {
    super();

    addSerializer(new PostalCodeSerializer());
    addDeserializer(PostalCode.class, new PostalCodeDeserializer());


  }
}
