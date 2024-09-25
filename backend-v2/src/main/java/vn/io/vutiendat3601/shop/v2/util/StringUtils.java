package vn.io.vutiendat3601.shop.v2.util;

import java.util.Random;

public class StringUtils {
  private static final String ALPHABET =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
  private static final Random RANDOM = new Random();

  public static String makeRandomDigits(int max) {
    return "%04d".formatted(RANDOM.nextInt(max + 1));
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
}
