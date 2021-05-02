package com.supensour.webflux.handler;

import com.supensour.model.constant.FieldNames;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.map.impl.DefaultListValueMap;
import com.supensour.webflux.sample.TestApplication;
import com.supensour.webflux.sample.dto.User;
import com.supensour.webflux.sample.handler.WebExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@AutoConfigureWebTestClient
@SpringBootTest(classes = TestApplication.class)
class AbstractWebExceptionHandlerTest {

  @Autowired
  private WebTestClient client;

  @Test
  void webExchangeBindException() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>();
    errors.add("id", "Blank");
    errors.add("name", "Blank");
    errors.add("createdDate", "Null");

    client.post()
        .uri("/webExchangeBindException")
        .bodyValue(User.builder().build())
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody()
        .jsonPath("code").isEqualTo(HttpStatus.BAD_REQUEST.value())
        .jsonPath("status").isEqualTo(HttpStatus.BAD_REQUEST.name())
        .jsonPath("data").doesNotExist()
        .jsonPath("errors").isEqualTo(errors.toMap())
        .jsonPath("page").doesNotExist()
        .jsonPath("requestId").isEqualTo(WebExceptionHandler.REQUEST_ID)
        .jsonPath("success").isEqualTo(false);
  }

  @ParameterizedTest(name = "ServerWebInputException <- {0}")
  @ArgumentsSource(AbstractWebExceptionHandlerTest$ServerWebInputExceptionArgumentFactory.class)
  void serverWebInputException(@SuppressWarnings("unused") String name, Map<String, Object> scenario) {
    //noinspection unchecked
    client.post()
        .uri(uri -> uri.path("/serverWebInputException")
            .queryParams((MultiValueMap<String, String>) scenario.get("queries"))
            .build())
        .bodyValue(scenario.get("body"))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody()
        .jsonPath("code").isEqualTo(HttpStatus.BAD_REQUEST.value())
        .jsonPath("status").isEqualTo(HttpStatus.BAD_REQUEST.name())
        .jsonPath("data").doesNotExist()
        .jsonPath("errors").value((Consumer<?>) scenario.get("errorsMatcher"))
        .jsonPath("page").doesNotExist()
        .jsonPath("requestId").isEqualTo(WebExceptionHandler.REQUEST_ID)
        .jsonPath("success").isEqualTo(false);
  }

  @Test
  void responseStatusException_notFound() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>();
    client.get()
        .uri("/responseStatusException/not-found-path")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
        .expectBody()
        .jsonPath("code").isEqualTo(HttpStatus.NOT_FOUND.value())
        .jsonPath("status").isEqualTo(HttpStatus.NOT_FOUND.name())
        .jsonPath("data").doesNotExist()
        .jsonPath("errors").isEqualTo(errors.toMap())
        .jsonPath("page").doesNotExist()
        .jsonPath("requestId").isEqualTo(WebExceptionHandler.REQUEST_ID)
        .jsonPath("success").isEqualTo(false);
  }

  @Test
  void returnableException() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>();
    client.get()
        .uri("/returnableException")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
        .expectBody()
        .jsonPath("code").isEqualTo(HttpStatus.NOT_FOUND.value())
        .jsonPath("status").isEqualTo(HttpStatus.NOT_FOUND.name())
        .jsonPath("data").doesNotExist()
        .jsonPath("errors").isEqualTo(errors.toMap())
        .jsonPath("page").doesNotExist()
        .jsonPath("requestId").isEqualTo(WebExceptionHandler.REQUEST_ID)
        .jsonPath("success").isEqualTo(false);
  }

  @Test
  void errorsException() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>();
    client.get()
        .uri("/errorsException")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
        .expectBody()
        .jsonPath("code").isEqualTo(HttpStatus.BAD_REQUEST.value())
        .jsonPath("status").isEqualTo(HttpStatus.BAD_REQUEST.name())
        .jsonPath("data").doesNotExist()
        .jsonPath("errors").isEqualTo(errors.toMap())
        .jsonPath("page").doesNotExist()
        .jsonPath("requestId").isEqualTo(WebExceptionHandler.REQUEST_ID)
        .jsonPath("success").isEqualTo(false);
  }

  @Test
  void throwable() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>(FieldNames.DEFAULT, "ServerError");
    client.get()
        .uri("/throwable")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        .expectBody()
        .jsonPath("code").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .jsonPath("status").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.name())
        .jsonPath("data").doesNotExist()
        .jsonPath("errors").isEqualTo(errors.toMap())
        .jsonPath("page").doesNotExist()
        .jsonPath("requestId").isEqualTo(WebExceptionHandler.REQUEST_ID)
        .jsonPath("success").isEqualTo(false);
  }

  @Test
  void throwable_responseStatus_default() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>(FieldNames.DEFAULT, "ServerError");
    client.get()
        .uri("/throwable/response-status/default")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        .expectBody()
        .jsonPath("code").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .jsonPath("status").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.name())
        .jsonPath("data").doesNotExist()
        .jsonPath("errors").isEqualTo(errors.toMap())
        .jsonPath("page").doesNotExist()
        .jsonPath("requestId").isEqualTo(WebExceptionHandler.REQUEST_ID)
        .jsonPath("success").isEqualTo(false);
  }

  @Test
  void throwable_responseStatus_code() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>(FieldNames.DEFAULT, "ServerError");
    client.get()
        .uri("/throwable/response-status/code")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.REQUEST_TIMEOUT)
        .expectBody()
        .jsonPath("code").isEqualTo(HttpStatus.REQUEST_TIMEOUT.value())
        .jsonPath("status").isEqualTo(HttpStatus.REQUEST_TIMEOUT.name())
        .jsonPath("data").doesNotExist()
        .jsonPath("errors").isEqualTo(errors.toMap())
        .jsonPath("page").doesNotExist()
        .jsonPath("requestId").isEqualTo(WebExceptionHandler.REQUEST_ID)
        .jsonPath("success").isEqualTo(false);
  }

  @Test
  void throwable_responseStatus_status() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>(FieldNames.DEFAULT, "ServerError");
    client.get()
        .uri("/throwable/response-status/status")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.REQUEST_TIMEOUT)
        .expectBody()
        .jsonPath("code").isEqualTo(HttpStatus.REQUEST_TIMEOUT.value())
        .jsonPath("status").isEqualTo(HttpStatus.REQUEST_TIMEOUT.name())
        .jsonPath("data").doesNotExist()
        .jsonPath("errors").isEqualTo(errors.toMap())
        .jsonPath("page").doesNotExist()
        .jsonPath("requestId").isEqualTo(WebExceptionHandler.REQUEST_ID)
        .jsonPath("success").isEqualTo(false);
  }

  @Test
  void ignoredException() {
    client.get()
        .uri("/ignoredException")
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        .expectBody()
        .isEmpty();
  }

}
