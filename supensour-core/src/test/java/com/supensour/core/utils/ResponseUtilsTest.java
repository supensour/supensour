package com.supensour.core.utils;

import com.supensour.model.common.PagingResponse;
import com.supensour.model.map.ListValueMap;
import com.supensour.model.map.impl.DefaultListValueMap;
import com.supensour.model.web.Response;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
class ResponseUtilsTest {

  private final static Long NUMBER = 0L;
  private final static Long SIZE = 20L;
  private final static Long NUMBER_OF_ELEMENTS = 2L;
  private final static Long TOTAL_ELEMENTS = 2L;
  private final static Long TOTAL_PAGES = 1L;
  private final static String CONTENT_1 = "1";
  private final static String CONTENT_2 = "2";
  private final static String REQUEST_ID = "requestId";


  @Test
  void status_requestId_data_page() {
    Response<List<String>> response = ResponseUtils
        .status(HttpStatus.OK, REQUEST_ID, createContent(), createPageResponse());
    assertStatus(response, HttpStatus.OK);
    assertPageResponse(response.getPage());
    assertContentString(response.getData());
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void status_requestId_data() {
    Response<List<String>> response = ResponseUtils.status(HttpStatus.OK, REQUEST_ID, createContent());
    assertStatus(response, HttpStatus.OK);
    assertContentString(response.getData());
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void status_requestId() {
    Response<String> response = ResponseUtils.status(HttpStatus.OK, REQUEST_ID);
    assertStatus(response, HttpStatus.OK);
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void status_requestId_errors() {
    Response<String> response = ResponseUtils.status(HttpStatus.BAD_REQUEST, REQUEST_ID, createErrors());
    assertStatus(response, HttpStatus.BAD_REQUEST);
    assertErrors(response.getErrors());
    assertNull(response.getData());
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void status_requestId_page_mapper() {
    Response<List<Integer>> response = ResponseUtils.status(HttpStatus.OK, REQUEST_ID, createPage(), this::parseInts);
    assertStatus(response, HttpStatus.OK);
    assertPageResponse(response.getPage());
    assertContentInteger(response.getData());
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void ok_requestId_data_page() {
    Response<Boolean> response = ResponseUtils.ok(REQUEST_ID, true, createPageResponse());
    assertStatus(response, HttpStatus.OK);
    assertPageResponse(response.getPage());
    assertTrue(response.getData());
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void ok_requestId_data() {
    Response<Boolean> response = ResponseUtils.ok(REQUEST_ID, true);
    assertStatus(response, HttpStatus.OK);
    assertTrue(response.getData());
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void ok_requestId() {
    Response<Boolean> response = ResponseUtils.ok(REQUEST_ID);
    assertStatus(response, HttpStatus.OK);
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void ok_requestId_page_mapper() {
    Response<List<Integer>> response = ResponseUtils.ok(REQUEST_ID, createPage(), this::parseInts);
    assertStatus(response, HttpStatus.OK);
    assertPageResponse(response.getPage());
    assertContentInteger(response.getData());
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void badRequest_requestId_errors_data() {
    Response<Boolean> response = ResponseUtils.badRequest(REQUEST_ID, createErrors(), true);
    assertStatus(response, HttpStatus.BAD_REQUEST);
    assertErrors(response.getErrors());
    assertTrue(response.getData());
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  @Test
  void badRequest_requestId_errors() {
    Response<Boolean> response = ResponseUtils.badRequest(REQUEST_ID, createErrors());
    assertStatus(response, HttpStatus.BAD_REQUEST);
    assertErrors(response.getErrors());
    assertNull(response.getData());
    assertEquals(REQUEST_ID, response.getRequestId());
  }

  private List<Integer> parseInts(Page<String> page) {
    return page.stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }

  private void assertPageResponse(PagingResponse pageResponse) {
    assertEquals(NUMBER, pageResponse.getNumber());
    assertEquals(SIZE, pageResponse.getSize());
    assertEquals(NUMBER_OF_ELEMENTS, pageResponse.getNumberOfElements());
    assertEquals(TOTAL_ELEMENTS, pageResponse.getTotalElements());
    assertEquals(TOTAL_PAGES, pageResponse.getTotalPages());
  }

  private void assertStatus(Response<?> response, HttpStatus status) {
    assertEquals(status.value(), response.getCode().intValue());
    assertEquals(status.name(), response.getStatus());
  }

  private void assertContentString(List<String> content) {
    assertEquals(2, content.size());
    assertEquals(CONTENT_1, content.get(0));
    assertEquals(CONTENT_2, content.get(1));
  }

  private void assertContentInteger(List<Integer> content) {
    assertEquals(2, content.size());
    assertEquals(Integer.parseInt(CONTENT_1), content.get(0).intValue());
    assertEquals(Integer.parseInt(CONTENT_2), content.get(1).intValue());
  }

  private void assertErrors(Map<String, List<String>> errors) {
    assertEquals(2, errors.size());
    assertEquals(1, errors.get("1").size());
    assertEquals(CollectionUtils.toList("Code1"), errors.get("1"));
    assertEquals(2, errors.get("2").size());
    assertEquals(CollectionUtils.toList("Code2", "Code3"), errors.get("2"));
  }

  private Page<String> createPage() {
    return new PageImpl<>(createContent(), createPageable(), TOTAL_ELEMENTS);
  }

  private List<String> createContent() {
    return Arrays.asList(CONTENT_1, CONTENT_2);
  }

  private Pageable createPageable() {
    return PageRequest.of(NUMBER.intValue(), SIZE.intValue());
  }

  private ListValueMap<String, String> createErrors() {
    return new DefaultListValueMap<String, String>()
        .add("1", "Code1")
        .add("2", "Code2")
        .add("2", "Code3");
  }

  private PagingResponse createPageResponse() {
    return PagingResponse.builder()
        .number(NUMBER)
        .size(SIZE)
        .numberOfElements(NUMBER_OF_ELEMENTS)
        .totalElements(TOTAL_ELEMENTS)
        .totalPages(TOTAL_PAGES)
        .build();
  }

}
