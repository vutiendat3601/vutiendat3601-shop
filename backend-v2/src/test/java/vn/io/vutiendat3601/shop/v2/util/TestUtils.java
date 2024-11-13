package vn.io.vutiendat3601.shop.v2.util;

import java.util.Base64;
import net.datafaker.Faker;

public class TestUtils {
  public static final Faker FAKER = new Faker();

  public static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();

  public static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();
}
