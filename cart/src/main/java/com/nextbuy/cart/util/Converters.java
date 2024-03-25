package com.nextbuy.cart.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbuy.cart.exception.FailedDependencyException;
import lombok.extern.slf4j.Slf4j;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.lang.String.format;


@Slf4j
public class Converters {

  private static final String ERROR_TO_PARSE = "Error deserializing json value to %s";
  private static final ObjectMapper mapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

  public static <T> T convertStringToObject(String objectString, Class<T> object) {
    try {
      return mapper.readValue(objectString, object);
    } catch (JsonProcessingException ex) {
      var message = format(ERROR_TO_PARSE, object.getName());
      log.error(message, ex);

      throw new FailedDependencyException(message, ex);
    }
  }
}
