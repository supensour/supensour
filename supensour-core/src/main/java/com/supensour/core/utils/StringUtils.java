package com.supensour.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils extends org.springframework.util.StringUtils {

  /**
   * Trim a string and count its length
   *
   * @param value string value
   * @return string length after it's trimmed
   */
  public static long getAfterTrimmedLength(String value) {
    return Optional.ofNullable(value)
        .map(String::trim)
        .map(String::length)
        .orElse(0);
  }

  /**
   * Get all matches with regular expression in a string value.
   *
   * @param value the string to be looked in
   * @param regex the regular expression for matching
   *
   * @return list of all matches
   */
  public static List<String> match(String value, String regex) {
    List<String> matches = new ArrayList<>();
    var matcher = getMatcher(value, regex);
    while (matcher.find()) {
      matches.add(matcher.group());
    }
    return matches;
  }

  /**
   * Get some matches (predetermined maximum count) with regular expression in a string value.
   *
   * @param value     the string to be looked in
   * @param regex     the regular expression for matching
   * @param maxCount  max count of matches
   *
   * @return list of all matches
   */
  public static List<String> match(String value, String regex, int maxCount) {
    List<String> matches = new ArrayList<>();
    var matcher = getMatcher(value, regex);
    while (matcher.find() && matches.size() < maxCount) {
      matches.add(matcher.group());
    }
    return matches;
  }

  /**
   * Get all substrings that is separated by a single character separator.
   * Empty string might be among them.
   *
   * @param value     the string to be looked in
   * @param separator the separator to split the string
   *
   * @return list of all substrings separated by the separator
   */
  public static List<String> split(String value, Character separator) {
    return match(value, buildSplitPattern(separator));
  }

  /**
   * Get some substrings (predetermined maximum count) that is separated by a single character separator.
   * Empty string might be among them.
   *
   * @param value     the string to be looked in
   * @param separator the separator to split the string
   * @param maxCount  max count of substrings
   *
   * @return list of all substrings separated by the separator
   */
  public static List<String> split(String value, Character separator, int maxCount) {
    return match(value, buildSplitPattern(separator), maxCount);
  }

  /**
   * Get substring from after a preliminary substring till the index of terminator (exclusive).
   * Might return the substring till the end of the string if no terminator is found.
   * Might also return empty string if the passed string value is empty or the preliminary substring can't be found.
   *
   * @param value       the string to be looked in
   * @param preliminary the starting preliminary string to start from
   * @param terminator  the ending terminator string to stop
   *
   * @return the substring
   */
  public static String subString(String value, String preliminary, String terminator) {
    if (org.springframework.util.StringUtils.isEmpty(preliminary)) {
      return Optional.ofNullable(value)
          .map(string -> subString(value, 0, terminator))
          .orElseGet(String::new);
    } else {
      return Optional.ofNullable(value)
          .map(string -> string.indexOf(preliminary))
          .filter(foundIndex -> foundIndex != -1)
          .map(foundIndex -> foundIndex + preliminary.length())
          .map(beginIndex -> subString(value, beginIndex, terminator))
          .orElseGet(String::new);
    }
  }

  /**
   * Get substring from given begin index till the index of terminator (exclusive).
   * Might return the substring till the end of the string if no terminator is found.
   * Might also return empty string if the passed string value is empty.
   *
   * @param value       the string to be looked in
   * @param beginIndex  the beginning index to start from
   * @param terminator  the ending terminator string to stop
   *
   * @return the substring
   */
  public static String subString(String value, int beginIndex, String terminator) {
    if (org.springframework.util.StringUtils.isEmpty(terminator)) {
      return Optional.ofNullable(value)
          .map(string -> string.substring(beginIndex))
          .orElseGet(String::new);
    } else {
      return Optional.ofNullable(value)
          .map(string -> string.indexOf(terminator, beginIndex))
          .map(foundIndex -> foundIndex != -1 ? foundIndex : value.length())
          .map(endIndex -> value.substring(beginIndex, endIndex))
          .orElseGet(String::new);
    }
  }

  /**
   * Get substring from after a preliminary substring till the end index (exclusive).
   * The end index might be out of the string length (will be normalized to the string length).
   * Might also return empty string if the passed string value is null or the preliminary substring can't be found.
   * The end index might be out of the string length (will be normalized to the string length).
   *
   * @param value       the string to be looked in
   * @param preliminary the beginning index to be looked from
   * @param endIndex    the terminator
   * @return the substring
   */
  public static String subString(String value, String preliminary, int endIndex) {
    if (org.springframework.util.StringUtils.isEmpty(preliminary)) {
      return Optional.ofNullable(value)
          .map(string -> string.substring(0, Math.min(endIndex, value.length())))
          .orElseGet(String::new);
    } else {
      return Optional.ofNullable(value)
          .map(string -> string.indexOf(preliminary))
          .filter(foundIndex -> foundIndex != -1)
          .map(foundIndex -> foundIndex + preliminary.length())
          .map(beginIndex -> value.substring(beginIndex, Math.min(endIndex, value.length())))
          .orElseGet(String::new);
    }
  }

  /**
   * Levenshtein Distance counts the minimum number of operations needed
   * to transform a string to another with deletion, insertion, and substitution.
   *
   * @param source      the string to be transformed
   * @param target      the string to be transformed into
   * @return the minimum number of operations needed for the transformation
   */
  public static int getLevenshteinDistance(String source, String target) {
    return getLevenshteinDistance(source, target, false);
  }

  /**
   * Levenshtein Distance counts the minimum number of operations needed
   * to transform a string to another with deletion, insertion, and substitution.
   *
   * @param source      the string to be transformed
   * @param target      the string to be transformed into
   * @param ignoreCase  whether to ignore case-sensitive matching
   * @return the minimum number of operations needed for the transformation
   */
  public static int getLevenshteinDistance(String source, String target, boolean ignoreCase) {
    int[] prevValues = IntStream.range(0, target.length() + 1).toArray();

    for(var i = 0; i < source.length(); i++) {
      var currValues = new int[target.length() + 1];
      currValues[0] = i + 1;

      for(var j = 0; j < target.length(); j++) {
        int deletionCost = prevValues[j + 1] + 1;
        int insertionCost = currValues[j] + 1;
        int substitutionCost = prevValues[j];

        if (ignoreCase) {
          if(!isEqualIgnoreCase(source.charAt(i), target.charAt(j))) {
            substitutionCost += 1;
          }
        } else {
          if(source.charAt(i) != target.charAt(j)) {
            substitutionCost += 1;
          }
        }
        currValues[j+1] = Stream.of(deletionCost, insertionCost, substitutionCost)
            .min(Integer::compareTo)
            .orElseThrow();
      }
      prevValues = currValues;
    }
    return prevValues[target.length()];
  }

  /**
   * This counts similarity value between two strings by using Levenshtein Distance
   * algorithm to get the value of the disparity which is needed in its computation
   *
   * @param str1 one of the strings to be matched
   * @param str2 another string to be matched
   * @return similarity value of two strings ranged from 0 - 1
   */
  public static double getSimilarityWithLevenshteinDistance(String str1, String str2) {
    return getSimilarityWithLevenshteinDistance(str1, str2, false);
  }

  /**
   * This counts similarity value between two strings by using Levenshtein Distance
   * algorithm to get the value of the disparity which is needed in its computation
   *
   * @param str1 one of the strings to be matched
   * @param str2 another string to be matched
   * @param ignoreCase  whether to ignore case-sensitive matching
   * @return similarity value of two strings ranged from 0 - 1
   */
  public static double getSimilarityWithLevenshteinDistance(String str1, String str2, boolean ignoreCase) {
    Objects.requireNonNull(str1, "str1 is null");
    Objects.requireNonNull(str2, "str2 is null");
    if(isEmpty(str1) && isEmpty(str2)) {
      return 1;
    }

    int disparity = getLevenshteinDistance(str1, str2, ignoreCase);
    int longestStringLength = Math.max(str1.length(), str2.length());
    int parity = longestStringLength - disparity;

    return (double) parity/longestStringLength;
  }

  public static boolean isEqualIgnoreCase(char a, char b) {
    return Character.toLowerCase(a) == Character.toLowerCase(b);
  }

  private static Matcher getMatcher(String value, String regex) {
    var pattern = Pattern.compile(regex);
    return pattern.matcher(value);
  }

  private static String buildSplitPattern(Character separator) {
    var withoutSeparator = "^[^%s]*$";
    var startBeforeSeparator = "^[^%s]*(?=%s)";
    var endAfterSeparator = "(?<=%s)[^%s]*$";
    var surroundedBySeparator = "(?<=%s)[^%s]*(?=%s)";
    return new StringBuilder()
        .append(String.format(withoutSeparator, separator)).append("|")
        .append(String.format(startBeforeSeparator, separator, separator)).append("|")
        .append(String.format(endAfterSeparator, separator, separator)).append("|")
        .append(String.format(surroundedBySeparator, separator, separator, separator))
        .toString();
  }

}
