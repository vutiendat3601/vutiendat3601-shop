package vn.io.vutiendat3601.shop.crawler.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Supplier;

public class StringUtils {
  private static final String ALPHABET =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
  private static final Supplier<Map<Character, Character>> UNACCENT_CHARACTERS_MAP_SUPPLIER =
      () -> {
        final String CHARACTERS = "Đđ";
        final String UNACCENT_CHARACTERS = "Dd";
        final Map<Character, Character> result = new HashMap<>();
        for (int i = 0; i < CHARACTERS.length(); i++) {
          result.put(CHARACTERS.charAt(i), UNACCENT_CHARACTERS.charAt(i));
        }
        return result;
      };
  private static final Map<Character, Character> UNACCENT_CHARACTERS_MAP =
      UNACCENT_CHARACTERS_MAP_SUPPLIER.get();
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

  public static String removeAccent(String text) {
    String result = Normalizer.normalize(text, Form.NFD).replaceAll("\\p{M}", "");
    for (Entry<Character, Character> cEntry : UNACCENT_CHARACTERS_MAP.entrySet()) {
      result = result.replace(cEntry.getKey(), cEntry.getValue());
    }
    return result.toString();
  }
}
