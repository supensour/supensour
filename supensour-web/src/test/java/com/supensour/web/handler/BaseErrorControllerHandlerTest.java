package com.supensour.web.handler;

import com.supensour.model.constant.ErrorCodes;
import com.supensour.model.constant.FieldNames;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.map.impl.DefaultListValueMap;
import com.supensour.web.sample.TestApplication;
import com.supensour.web.sample.dto.User;
import com.supensour.web.sample.handler.ErrorController;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.nullValue;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = { TestApplication.class })
class BaseErrorControllerHandlerTest {

  @Value("${local.server.port}")
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  void methodArgumentNotValidException() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>();
    errors.add("id", "Blank");
    errors.add("name", "Blank");
    errors.add("createdDate", "Null");

    given()
        .body(User.builder().build())
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .when()
        .post("/methodArgumentNotValidException")
        .then()
        .body("code", equalTo(HttpStatus.BAD_REQUEST.value()))
        .body("status", equalTo(HttpStatus.BAD_REQUEST.name()))
        .body("data", nullValue())
        .body("errors", equalTo(errors.toMap()))
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void missingServletRequestParameterException() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>();
    errors.add("number", "Null");

    given()
        .accept(ContentType.JSON)
        .when()
        .post("/missingServletRequestParameterException")
        .then()
        .body("code", equalTo(HttpStatus.BAD_REQUEST.value()))
        .body("status", equalTo(HttpStatus.BAD_REQUEST.name()))
        .body("data", nullValue())
        .body("errors", equalTo(errors.toMap()))
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void methodArgumentTypeMismatchException() {
    given()
        .when()
        .accept(ContentType.JSON)
        .queryParam("number", "test")
        .post("/methodArgumentTypeMismatchException")
        .then()
        .body("code", equalTo(HttpStatus.BAD_REQUEST.value()))
        .body("status", equalTo(HttpStatus.BAD_REQUEST.name()))
        .body("data", nullValue())
        .body("errors", aMapWithSize(1))
        .body("errors.number", iterableWithSize(2))
        .body("errors.number[0]", equalTo(ErrorCodes.INVALID_TYPE))
        .body("errors.number[1]", matchesPattern("^Failed to convert value.+"))
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void bindException() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>();
    errors.add("createdDate", ErrorCodes.INVALID_TYPE);
    given()
        .when()
        .accept(ContentType.JSON)
        .queryParam("createdDate", "date")
        .get("/bindException")
        .then()
        .body("code", equalTo(HttpStatus.BAD_REQUEST.value()))
        .body("status", equalTo(HttpStatus.BAD_REQUEST.name()))
        .body("data", nullValue())
        .body("errors", equalTo(errors.toMap()))
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void noHandlerFoundException() {
    given()
        .when()
        .accept(ContentType.JSON)
        .get("/noHandlerFoundException/not-found-path")
        .then()
        .body("code", equalTo(HttpStatus.NOT_FOUND.value()))
        .body("status", equalTo(HttpStatus.NOT_FOUND.name()))
        .body("data", nullValue())
        .body("errors", anEmptyMap())
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void httpRequestMethodNotSupportedException() {
    given()
        .when()
        .accept(ContentType.JSON)
        .post("/httpRequestMethodNotSupportedException")
        .then()
        .body("code", equalTo(HttpStatus.METHOD_NOT_ALLOWED.value()))
        .body("status", equalTo(HttpStatus.METHOD_NOT_ALLOWED.name()))
        .body("data", nullValue())
        .body("errors", anEmptyMap())
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
  }

  @Test
  void httpMessageNotReadableException() {
    given()
        .body("invalid-body")
        .when()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .post("/httpMessageNotReadableException")
        .then()
        .body("code", equalTo(HttpStatus.BAD_REQUEST.value()))
        .body("status", equalTo(HttpStatus.BAD_REQUEST.name()))
        .body("data", nullValue())
        .body("errors", aMapWithSize(1))
        .body("errors.default", iterableWithSize(2))
        .body("errors.default[0]", equalTo(ErrorCodes.INVALID))
        .body("errors.default[1]", matchesPattern("^Unrecognized token 'invalid'.+"))
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void httpMediaTypeNotSupportedException() {
    given()
        .when()
        .accept(ContentType.JSON)
        .contentType(ContentType.TEXT)
        .post("/httpMediaTypeNotSupportedException")
        .then()
        .body("code", equalTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
        .body("status", equalTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.name()))
        .body("data", nullValue())
        .body("errors", anEmptyMap())
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
  }

  @Test
  void httpMediaTypeNotAcceptableException() {
    given()
        .when()
        .accept(ContentType.JSON)
        .post("/httpMediaTypeNotAcceptableException")
        .then()
        .body("code", equalTo(HttpStatus.NOT_ACCEPTABLE.value()))
        .body("status", equalTo(HttpStatus.NOT_ACCEPTABLE.name()))
        .body("data", nullValue())
        .body("errors", anEmptyMap())
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
  }

  @Test
  void responseStatusException() {
    given()
        .when()
        .accept(ContentType.JSON)
        .get("/responseStatusException")
        .then()
        .body("code", equalTo(HttpStatus.BAD_GATEWAY.value()))
        .body("status", equalTo(HttpStatus.BAD_GATEWAY.name()))
        .body("data", nullValue())
        .body("errors", anEmptyMap())
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.BAD_GATEWAY.value());
  }

  @Test
  void returnableException() {
    given()
        .when()
        .accept(ContentType.JSON)
        .get("/returnableException")
        .then()
        .body("code", equalTo(HttpStatus.NOT_FOUND.value()))
        .body("status", equalTo(HttpStatus.NOT_FOUND.name()))
        .body("data", nullValue())
        .body("errors", anEmptyMap())
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void errorsException() {
    given()
        .when()
        .accept(ContentType.JSON)
        .get("/errorsException")
        .then()
        .body("code", equalTo(HttpStatus.BAD_REQUEST.value()))
        .body("status", equalTo(HttpStatus.BAD_REQUEST.name()))
        .body("data", nullValue())
        .body("errors", anEmptyMap())
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void throwable() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>(FieldNames.DEFAULT, ErrorCodes.SERVER_ERROR);
    given()
        .when()
        .accept(ContentType.JSON)
        .get("/throwable")
        .then()
        .body("code", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()))
        .body("status", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.name()))
        .body("data", nullValue())
        .body("errors", equalTo(errors.toMap()))
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  @Test
  void throwable_responseStatus_default() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>(FieldNames.DEFAULT, ErrorCodes.SERVER_ERROR);
    given()
        .when()
        .accept(ContentType.JSON)
        .get("/throwable/response-status/default")
        .then()
        .body("code", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()))
        .body("status", equalTo(HttpStatus.INTERNAL_SERVER_ERROR.name()))
        .body("data", nullValue())
        .body("errors", equalTo(errors))
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  @Test
  void throwable_responseStatus_code() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>(FieldNames.DEFAULT, ErrorCodes.SERVER_ERROR);
    given()
        .when()
        .accept(ContentType.JSON)
        .get("/throwable/response-status/code")
        .then()
        .body("code", equalTo(HttpStatus.REQUEST_TIMEOUT.value()))
        .body("status", equalTo(HttpStatus.REQUEST_TIMEOUT.name()))
        .body("data", nullValue())
        .body("errors", equalTo(errors))
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.REQUEST_TIMEOUT.value());
  }

  @Test
  void throwable_responseStatus_status() {
    ListValueMap<String, String> errors = new DefaultListValueMap<>(FieldNames.DEFAULT, ErrorCodes.SERVER_ERROR);
    given()
        .when()
        .accept(ContentType.JSON)
        .get("/throwable/response-status/status")
        .then()
        .body("code", equalTo(HttpStatus.REQUEST_TIMEOUT.value()))
        .body("status", equalTo(HttpStatus.REQUEST_TIMEOUT.name()))
        .body("data", nullValue())
        .body("errors", equalTo(errors))
        .body("page", nullValue())
        .body("requestId", equalTo(ErrorController.REQUEST_ID))
        .body("success", is(false))
        .statusCode(HttpStatus.REQUEST_TIMEOUT.value());
  }

}
