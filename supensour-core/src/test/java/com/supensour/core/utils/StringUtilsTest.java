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

  @Test
  void getLevenshteinDistance() {
    assertEquals(3, StringUtils.getLevenshteinDistance("kitten", "sitting"));
    assertEquals(3, StringUtils.getLevenshteinDistance("sitting", "kitten"));
    assertEquals(1, StringUtils.getLevenshteinDistance("a random string", "a randoms string"));
    assertEquals(1, StringUtils.getLevenshteinDistance("a randoms string", "a random string"));
    assertEquals(0, StringUtils.getLevenshteinDistance("same string", "same string"));
    assertEquals(0, StringUtils.getLevenshteinDistance("same string", "same string"));
    assertEquals(0, StringUtils.getLevenshteinDistance("", ""));
    assertEquals(4, StringUtils.getLevenshteinDistance("", "test"));
    assertEquals(4, StringUtils.getLevenshteinDistance("test", ""));
    assertEquals(0, StringUtils.getLevenshteinDistance("test", "test"));
    assertEquals(7, StringUtils.getLevenshteinDistance("a random string", "b rant0m 5+n1ng"));
    assertEquals(7, StringUtils.getLevenshteinDistance("b rant0m 5+n1ng", "a random string"));
    assertEquals(43, StringUtils.getLevenshteinDistance("a random string again and again, a long one", "z"));
    assertEquals(43, StringUtils.getLevenshteinDistance("z", "a random string again and again, a long one"));
    assertEquals(34, StringUtils.getLevenshteinDistance("a random string again and again, a long one", "b rant0m 5+n1ng"));
    assertEquals(34, StringUtils.getLevenshteinDistance("b rant0m 5+n1ng", "a random string again and again, a long one"));
    assertEquals(14, StringUtils.getLevenshteinDistance("a random string", "asdb rant0m df5+n1ngasd"));
    assertEquals(14, StringUtils.getLevenshteinDistance("asdb rant0m df5+n1ngasd", "a random string"));
    assertEquals(0, StringUtils.getLevenshteinDistance("kitten", "KITTEN", true));
  }

  @Test
  void getSimilarityWithLevenshteinDistance() {
    assertEquals(0.57142, getSimilarityWithLevenshteinDistance("kitten", "sitting"));
    assertEquals(0.57142, getSimilarityWithLevenshteinDistance("sitting", "kitten"));
    assertEquals(0.9375, getSimilarityWithLevenshteinDistance("a random string", "a randoms string"));
    assertEquals(0.9375, getSimilarityWithLevenshteinDistance("a randoms string", "a random string"));
    assertEquals(1, getSimilarityWithLevenshteinDistance("same string", "same string"));
    assertEquals(1, getSimilarityWithLevenshteinDistance("same string", "same string"));
    assertEquals(1, getSimilarityWithLevenshteinDistance("", ""));
    assertEquals(0, getSimilarityWithLevenshteinDistance("", "test"));
    assertEquals(0, getSimilarityWithLevenshteinDistance("test", ""));
    assertEquals(1, getSimilarityWithLevenshteinDistance("test", "test"));
    assertEquals(0.53333, getSimilarityWithLevenshteinDistance("a random string", "b rant0m 5+n1ng"));
    assertEquals(0.53333, getSimilarityWithLevenshteinDistance("b rant0m 5+n1ng", "a random string"));
    assertEquals(0, getSimilarityWithLevenshteinDistance("a random string again and again, a long one", "z"));
    assertEquals(0, getSimilarityWithLevenshteinDistance("z", "a random string again and again, a long one"));
    assertEquals(0.20930, getSimilarityWithLevenshteinDistance("a random string again and again, a long one", "b rant0m 5+n1ng"));
    assertEquals(0.20930, getSimilarityWithLevenshteinDistance("b rant0m 5+n1ng", "a random string again and again, a long one"));
    assertEquals(0.39130, getSimilarityWithLevenshteinDistance("a random string", "asdb rant0m df5+n1ngasd"));
    assertEquals(0.39130, getSimilarityWithLevenshteinDistance("asdb rant0m df5+n1ngasd", "a random string"));
    assertEquals(1, getSimilarityWithLevenshteinDistance("kitten", "KITTEN", true));
    assertEquals(0, getSimilarityWithLevenshteinDistance("kitten", "KITTEN", false));
    assertEquals(1, getSimilarityWithLevenshteinDistance("same string", "same string", true));
    assertEquals(1, getSimilarityWithLevenshteinDistance("same string", "same string", false));
  }

  private double getSimilarityWithLevenshteinDistance(String str1, String str2) {
    double result = StringUtils.getSimilarityWithLevenshteinDistance(str1, str2);
    return ((int) (result * 100_000)) / 100_000.0;
  }

  private double getSimilarityWithLevenshteinDistance(String str1, String str2, boolean ignoreCase) {
    double result = StringUtils.getSimilarityWithLevenshteinDistance(str1, str2, ignoreCase);
    return ((int) (result * 100_000)) / 100_000.0;
  }

}
