package vn.io.vutiendat3601.shop.v2.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class ObjectMapperUtilsTest {
  @SuppressWarnings("null")
  @Test
  void willReturnEmptyWhenAtLeastArgumentIsNull() {
    // Given
    final Optional<?> expected = Optional.empty();
    // When
    final Optional<?> actual1 = ObjectMapperUtils.readValue(null, ObjectMapperUtilsTest.class);
    final Optional<?> actual2 = ObjectMapperUtils.readValue("serializedText", null);
    final Optional<?> actual3 = ObjectMapperUtils.readValue(null, null);

    //  Then
    assertEquals(actual1, expected);
    assertEquals(actual2, expected);
    assertEquals(actual3, expected);
  }

  @Test
  void willReturnEmptyWhenThrowsJsonProcessingException() {
    // Given
    final Optional<ObjectMapperUtilsTest> expected = Optional.empty();

    // When
    final Optional<ObjectMapperUtilsTest> actual =
        ObjectMapperUtils.readValue("null", ObjectMapperUtilsTest.class);

    // Then
    assertEquals(expected, actual);
  }
}
