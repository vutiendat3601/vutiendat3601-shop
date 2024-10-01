package vn.io.vutiendat3601.shop.v2.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class MissingConfigurationException extends RuntimeException {
  private final String key;

  public MissingConfigurationException(String message, String key) {
    super(message);
    this.key = key;
  }
}
