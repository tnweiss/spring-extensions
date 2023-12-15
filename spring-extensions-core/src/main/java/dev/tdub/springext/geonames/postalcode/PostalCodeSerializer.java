package dev.tdub.springext.geonames.postalcode;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PostalCodeSerializer extends JsonSerializer<PostalCode> {
  @Override
  public void serialize(PostalCode value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getCode());
  }

//  public static class Con implements jakarta.persistence.AttributeConverter<PostalCode, String> {
//    @Override
//    public String convertToDatabaseColumn(PostalCode attribute) {
//      return Optional.ofNullable(attribute)
//          .map(PostalCode::getCode)
//          .orElse(null);
//    }
//
//    @Override
//    public PostalCode convertToEntityAttribute(String dbData) {
//      return PostalCodeDto.fromPostalCode(dbData);
//    }
//  }
//
//  public static class Des extends JsonDeserializer<PostalCode> implements Converter<String, PostalCode>, com.fasterxml.jackson.databind.util.Converter {
//    @Override
//    public PostalCode convert(@NonNull String source) {
//      return PostalCodeDto.fromPostalCode(source);
//    }
//
//    @Override
//    public PostalCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
//      return PostalCodeDto.fromPostalCode(p.getValueAsString());
//    }
//  }
//
//  public static class Ser extends JsonSerializer<PostalCode> implements Converter<PostalCode, String> {
//    @Override
//    public void serialize(PostalCode value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//      gen.writeString(value.getCode());
//    }
//
//    @Override
//    public String convert(PostalCode source) {
//      return source.getCode();
//    }
//  }
}
