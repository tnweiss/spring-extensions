package dev.tdub.springext.geonames.postalcode;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import dev.tdub.springext.geonames.PostalCode;

public class PostalCodeDeserializer extends JsonDeserializer<PostalCode> {
  @Override
  public PostalCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    return PostalCodeDto.fromPostalCode(p.getValueAsString());
  }
}
