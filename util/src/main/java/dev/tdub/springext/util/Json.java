package com.ora.web.common.serdes;

import java.text.DateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.tdub.springext.error.exceptions.InternalServerException;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class Json {
//  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
//      .registerModule(new JavaTimeModule());
  private static final ObjectMapper OBJECT_MAPPER = new Jackson2ObjectMapperBuilder()
    .dateFormat(DateFormat.getInstance())
    .build();

  public static <T> String json(T object) {
    try {
      return OBJECT_MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException ex) {
      throw new InternalServerException(ex);
    }
  }

  public static <T> T object(String object, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(object, clazz);
    } catch (JsonProcessingException ex) {
      throw new InternalServerException(ex);
    }
  }
}
