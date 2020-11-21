package com.supensour.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {

  protected static final Random random = new SecureRandom();

  public enum RandomType {
    DIGIT,
    ALPHABET,
    ALPHANUMERIC
  }

  /**
   * Generate random sequence of digits with given length.
   *
   * @param length  the length of the sequence to be generated
   *
   * @return random sequence of digits
   */
  public static String digit(int length) {
    StringBuilder result = new StringBuilder();
    while (result.length() < length) {
      result.append(random.nextInt((int) Math.pow(10, length)));
    }
    return result.substring(0, length);
  }

  /**
   * Generate random sequence of alphabets with given length.
   *
   * @param length  the length of the sequence to be generated
   *
   * @return random sequence of alphabets
   */
  public static String alphabet(int length) {
    StringBuilder result = new StringBuilder();
    while (result.length() < length) {
      result.append(UUID.randomUUID().toString().replaceAll("[^a-zA-Z]", ""));
    }
    return result.substring(0, length);
  }

  /**
   * Generate random sequence of alphabets and digits with given length.
   *
   * @param length  the length of the sequence to be generated
   *
   * @return random sequence of alphabets
   */
  public static String alphaNumeric(int length) {
    StringBuilder result = new StringBuilder();
    while (result.length() < length) {
      result.append(UUID.randomUUID().toString().replaceAll("[^a-zA-Z0-9]", ""));
    }
    return result.substring(0, length);
  }

  /**
   * Generate random sequence of given type with given length.
   *
   * @param type    the type of random sequence, {@link RandomType}
   * @param length  the length of the sequence to be generated
   *
   * @throws IllegalArgumentException if invalid random type is given
   * @return Random generated sequence.
   */
  public static String generate(RandomType type, int length) {
    switch (type) {
      case ALPHABET:
        return alphabet(length);
      case ALPHANUMERIC:
        return alphaNumeric(length);
      case DIGIT:
      default:
        return digit(length);
    }
  }

}
