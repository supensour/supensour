package com.supensour.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
class ConverterUtilsTest {

  @Test
  void copyPropertiesSingle() {
    ModelA a = new ModelA("valueA", "valueB");
    ModelB b = ConverterUtils.copyProperties(a, new ModelB());
    assertEquals("valueA", b.fieldA);
    assertEquals("valueB", b.fieldB);

    b = ConverterUtils.copyProperties(a, new ModelB(), "fieldB");
    assertEquals("valueA", b.fieldA);
    assertNull(b.fieldB);

    b = ConverterUtils.copyProperties(a, new ModelB(), "fieldA", "fieldB");
    assertNull(b.fieldA);
    assertNull(b.fieldB);

    b = ConverterUtils.copyProperties(a, new ModelB("A", "B"), "fieldA", "fieldB");
    assertEquals("A", b.fieldA);
    assertEquals("B", b.fieldB);
  }

  @Test
  void copyPropertiesSingleSupplier() {
    ModelA a = new ModelA("valueA", "valueB");
    ModelB b = ConverterUtils.copyProperties(a, ModelB::new);
    assertEquals("valueA", b.fieldA);
    assertEquals("valueB", b.fieldB);

    b = ConverterUtils.copyProperties(a, ModelB::new, "fieldB");
    assertEquals("valueA", b.fieldA);
    assertNull(b.fieldB);
  }

  @Test
  void copyPropertiesSingleSupplier_nullSupplier() {
    try {
      ModelA a = new ModelA("valueA", "valueB");
      ConverterUtils.copyProperties(a, null);
    } catch (RuntimeException e) {
      assertEquals("Target supplier must not be null", e.getMessage());
    }
  }

  @Test
  void copyPropertiesList() {
    List<ModelA> as = new ArrayList<>();
    as.add(new ModelA("valueA1", "valueB1"));
    as.add(new ModelA("valueA2", "valueB2"));
    List<ModelB> bs = ConverterUtils.copyProperties(as, ModelB::new);
    assertEquals("valueA1", bs.get(0).fieldA);
    assertEquals("valueB1", bs.get(0).fieldB);
    assertEquals("valueA2", bs.get(1).fieldA);
    assertEquals("valueB2", bs.get(1).fieldB);

    bs = ConverterUtils.copyProperties(null, ModelB::new);
    assertEquals(0, bs.size());

    bs = ConverterUtils.copyProperties(new ArrayList<>(), ModelB::new);
    assertEquals(0, bs.size());
  }

  @Test
  void copyPropertiesList_nullSupplier() {
    try {
      ConverterUtils.copyProperties(null, null);
    } catch (RuntimeException e) {
      assertEquals("Target supplier must not be null", e.getMessage());
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class ModelA {
    String fieldA;
    String fieldB;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class ModelB {
    String fieldA;
    String fieldB;
  }

}
