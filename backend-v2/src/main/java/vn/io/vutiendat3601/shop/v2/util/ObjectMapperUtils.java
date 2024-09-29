package vn.io.vutiendat3601.shop.v2.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import org.springframework.lang.NonNull;

@Slf4j
public class ObjectMapperUtils {
  private static final ObjectMapper objMapper;

  static {
    objMapper = new ObjectMapper();
    objMapper.findAndRegisterModules();
  }

  public static <T> Optional<T> readValue(@NonNull String serializedText, @NonNull Class<T> clazz) {
    if (Objects.isNull(serializedText) || Objects.isNull(clazz)) {
      return Optional.empty();
    }
    try {
      return Optional.of(objMapper.readValue(serializedText, clazz));
    } catch (Exception e) {
      log.error("Can't read json value", e);
    }
    return Optional.empty();
  }
}
