package vn.io.vutiendat3601.shop.v2.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

public class ResponseUtils {
  public static <T> ResponseEntity<T> generateResponse(T data) {
    return ResponseEntity.ok(data);
  }

  public static <T> ResponseEntity<T> generateResponse(@NonNull HttpStatus status, T data) {
    return ResponseEntity.status(status.value()).body(data);
  }
}
