package vn.io.vutiendat3601.shop.v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ResourceDuplicationException extends RuntimeException {
  public ResourceDuplicationException(String message) {
    super(message);
  }
}