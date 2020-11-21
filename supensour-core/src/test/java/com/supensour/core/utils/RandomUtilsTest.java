package com.supensour.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
class RandomUtilsTest {

  @Test
  void testNumber() {
    for (int i=0; i<20; i++) {
      assertTrue(RandomUtils.generate(RandomUtils.RandomType.DIGIT, 8).matches("^[0-9]{8}$"));
    }
  }

  @Test
  void testAlphabet() {
    for (int i=0; i<20; i++) {
      assertTrue(RandomUtils.generate(RandomUtils.RandomType.ALPHABET, 8).matches("^[a-zA-Z]{8}$"));
    }
  }

  @Test
  void testAlphaNumeric() {
    for (int i=0; i<20; i++) {
      assertTrue(RandomUtils.generate(RandomUtils.RandomType.ALPHANUMERIC, 8).matches("^[a-zA-Z0-9]{8}$"));
    }
  }

}
