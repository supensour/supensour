package com.supensour.webflux.handler;

import com.supensour.model.constant.ErrorCodes;
import com.supensour.model.constant.FieldNames;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
public class AbstractWebExceptionHandlerTest$ServerWebInputExceptionArgumentFactory implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(
        typeMismatchException(),
        decodingException_JsonMappingException(),
        decodingException_JsonParseException(),
        noCause()
    ).map(scenario -> Arguments.of(scenario.get("name"), scenario));
  }

  private static Map<String, Object> typeMismatchException() {
    MultiValueMap<String, String> queries = new LinkedMultiValueMap<>();
    queries.add("number", "number");

    Consumer<Map<String, List<String>>> errorMatcher = errors -> {
      assertEquals(1, errors.size());
      assertEquals(1, errors.get("number").size());
      assertEquals(ErrorCodes.INVALID_TYPE, errors.get("number").get(0));
    };

    Map<String, Object> scenario = new HashMap<>();
    scenario.put("name", "TypeMismatchException");
    scenario.put("body", "{}");
    scenario.put("queries", queries);
    scenario.put("errorsMatcher", errorMatcher);
    return scenario;
  }

  private static Map<String, Object> decodingException_JsonMappingException() {
    MultiValueMap<String, String> queries = new LinkedMultiValueMap<>();
    queries.add("number", "0");

    Consumer<Map<String, List<String>>> errorMatcher = errors -> {
      assertEquals(1, errors.size());
      assertEquals(2, errors.get(FieldNames.REQUEST_BODY).size());
      assertTrue(errors.get(FieldNames.REQUEST_BODY).stream().anyMatch(s -> s.contains("deserialize from boolean value")));
      assertTrue(errors.get(FieldNames.REQUEST_BODY).contains(ErrorCodes.INVALID));
    };

    Map<String, Object> scenario = new HashMap<>();
    scenario.put("name", "DecodingException <- JsonMappingException");
    scenario.put("body", "true");
    scenario.put("queries", queries);
    scenario.put("errorsMatcher", errorMatcher);
    return scenario;
  }

  private static Map<String, Object> decodingException_JsonParseException() {
    MultiValueMap<String, String> queries = new LinkedMultiValueMap<>();
    queries.add("number", "0");

    Consumer<Map<String, List<String>>> errorMatcher = errors -> {
      assertEquals(1, errors.size());
      assertEquals(2, errors.get(FieldNames.REQUEST_BODY).size());
      assertTrue(errors.get(FieldNames.REQUEST_BODY).stream().anyMatch(s -> s.contains("Unexpected character ('t'")));
      assertTrue(errors.get(FieldNames.REQUEST_BODY).contains(ErrorCodes.INVALID));
    };

    Map<String, Object> scenario = new HashMap<>();
    scenario.put("name", "DecodingException <- JsonParseException");
    scenario.put("body", "{true}");
    scenario.put("queries", queries);
    scenario.put("errorsMatcher", errorMatcher);
    return scenario;
  }

  private static Map<String, Object> noCause() {
    MultiValueMap<String, String> queries = new LinkedMultiValueMap<>();

    Consumer<Map<String, List<String>>> errorMatcher = errors -> {
      assertEquals(1, errors.size());
      assertEquals(2, errors.get("number").size());
      assertTrue(errors.get("number").contains("Required Integer parameter 'number' is not present"));
      assertTrue(errors.get("number").contains(ErrorCodes.INVALID));
    };

    Map<String, Object> scenario = new HashMap<>();
    scenario.put("name", "noCause");
    scenario.put("body", "{}");
    scenario.put("queries", queries);
    scenario.put("errorsMatcher", errorMatcher);
    return scenario;
  }

}
