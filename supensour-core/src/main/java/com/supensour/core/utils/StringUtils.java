package com.supensour.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

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
    Matcher matcher = getMatcher(value, regex);
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
    Matcher matcher = getMatcher(value, regex);
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

  private static Matcher getMatcher(String value, String regex) {
    Pattern pattern = Pattern.compile(regex);
    return pattern.matcher(value);
  }

  private static String buildSplitPattern(Character separator) {
    String withoutSeparator = "^[^%s]*$";
    String startBeforeSeparator = "^[^%s]*(?=%s)";
    String endAfterSeparator = "(?<=%s)[^%s]*$";
    String surroundedBySeparator = "(?<=%s)[^%s]*(?=%s)";
    return new StringBuilder()
        .append(String.format(withoutSeparator, separator)).append("|")
        .append(String.format(startBeforeSeparator, separator, separator)).append("|")
        .append(String.format(endAfterSeparator, separator, separator)).append("|")
        .append(String.format(surroundedBySeparator, separator, separator, separator))
        .toString();
  }

}
