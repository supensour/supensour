package com.supensour.core.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
class StringUtilsTest {

  @Test
  void getAfterTrimmedLength_valid_success() {
    assertEquals(0, StringUtils.getAfterTrimmedLength(null));
    assertEquals(0, StringUtils.getAfterTrimmedLength(""));
    assertEquals(0, StringUtils.getAfterTrimmedLength(" \t \n \r\n "));
    assertEquals(4, StringUtils.getAfterTrimmedLength("Test"));
    assertEquals(4, StringUtils.getAfterTrimmedLength(" Test \t\n\r\n"));
  }

  @Test
  void match() {
    List<String> matches = StringUtils.match("supra-supra", "(su|ra)");
    assertEquals(4, matches.size());
    assertEquals("su", matches.get(0));
    assertEquals("ra", matches.get(1));
    assertEquals("su", matches.get(2));
    assertEquals("ra", matches.get(3));
  }

  @Test
  void match_max() {
    List<String> matches = StringUtils.match("supra-supra", "(su|ra)", 3);
    assertEquals(3, matches.size());
    assertEquals("su", matches.get(0));
    assertEquals("ra", matches.get(1));
    assertEquals("su", matches.get(2));

    matches = StringUtils.match("supra-supra", "(su|ra)", 4);
    assertEquals(4, matches.size());
    assertEquals("su", matches.get(0));
    assertEquals("ra", matches.get(1));
    assertEquals("su", matches.get(2));
    assertEquals("ra", matches.get(3));
  }

  @Test
  void split() {
    List<String> results = StringUtils.split("supra-supra--supra", '-');
    assertEquals(4, results.size());
    assertEquals("supra", results.get(0));
    assertEquals("supra", results.get(1));
    assertEquals("", results.get(2));
    assertEquals("supra", results.get(3));

    results = StringUtils.split("supra", '-');
    assertEquals(1, results.size());
    assertEquals("supra", results.get(0));
  }

  @Test
  void split_max() {
    List<String> results = StringUtils.split("supra-supra--supra", '-', 2);
    assertEquals(2, results.size());
    assertEquals("supra", results.get(0));
    assertEquals("supra", results.get(1));
  }

  @Test
  void subString_preliminary_terminator() {
    String text = "Lorem ipsum dolor sit";
    assertEquals("ipsum dolor", StringUtils.subString(text, "Lorem ", " sit"));
    assertEquals("Lorem ipsum dolor", StringUtils.subString(text, "", " sit"));
    assertEquals("Lorem ipsum dolor", StringUtils.subString(text, null, " sit"));
    assertEquals("ipsum dolor sit", StringUtils.subString(text, "Lorem ", " sits"));
    assertEquals("", StringUtils.subString(text, "Lorems", " sit"));
  }

  @Test
  void subString_beginIndex_terminator() {
    String text = "Lorem ipsum dolor sit";
    assertEquals("e", StringUtils.subString(text, 3, "m"));
    assertEquals("", StringUtils.subString(text, 10, "m"));
    assertEquals("dolor sit", StringUtils.subString(text, 12, null));
    assertEquals("dolor sit", StringUtils.subString(text, 12, ""));
    assertEquals(" dolor sit", StringUtils.subString(text, 11, "m"));
    assertEquals("Lore", StringUtils.subString(text, 0, "m"));
    assertEquals("ipsu", StringUtils.subString(text, 6, "m"));
  }

  @Test
  void subString_preliminary_endIndex() {
    String text = "Lorem ipsum dolor sit";
    assertEquals("ipsum", StringUtils.subString(text, "Lorem ", 11));
    assertEquals("ipsum dolor sit", StringUtils.subString(text, "Lorem ", 255));
    assertEquals("", StringUtils.subString(text, "Lorems", 11));
    assertEquals("Lorem", StringUtils.subString(text, "", 5));
    assertEquals("Lorem", StringUtils.subString(text, null, 5));
    assertEquals("Lorem ipsum dolor sit", StringUtils.subString(text, null, 255));
  }

}
