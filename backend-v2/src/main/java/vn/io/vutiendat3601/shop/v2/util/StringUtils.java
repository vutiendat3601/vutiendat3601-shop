package vn.io.vutiendat3601.shop.v2.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.util.Assert;

public class StringUtils {
  private static final Mac HMAC_512;
  private static final String ALPHABET =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
  private static final String DIGITS = "0123456789";
  private static final Random RANDOM = new Random();

  static {
    try {
      HMAC_512 = Mac.getInstance("HmacSHA512");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public static String makeRandomDigits(int length) {
    if (length < 0) {
      throw new IllegalArgumentException("length must be bigger than or equals to 0");
    }
    final int n = DIGITS.length();
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < length; i++) {
      result.append(DIGITS.charAt(RANDOM.nextInt(n)));
    }
    return result.toString();
  }

  public static String makeRandomString(int length) {
    if (length < 0) {
      throw new IllegalArgumentException("length must be bigger than or equals to 0");
    }
    final int n = ALPHABET.length();
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < length; i++) {
      result.append(ALPHABET.charAt(RANDOM.nextInt(n)));
    }
    return result.toString();
  }

  public static String hmacSha512(final String key, final String data) {
    Assert.notNull(key, "key argument must not be null");
    Assert.notNull(data, "data argument must not be null");
    try {
      byte[] hmacKeyBytes = key.getBytes();
      final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
      HMAC_512.init(secretKey);
      byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
      byte[] result = HMAC_512.doFinal(dataBytes);
      StringBuilder hashBuilder = new StringBuilder(2 * result.length);
      for (byte b : result) {
        hashBuilder.append(String.format("%02x", b & 0xff));
      }
      return hashBuilder.toString();
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
