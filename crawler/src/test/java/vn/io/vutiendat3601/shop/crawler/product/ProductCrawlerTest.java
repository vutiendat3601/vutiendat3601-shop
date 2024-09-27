package vn.io.vutiendat3601.shop.crawler.product;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import vn.io.vutiendat3601.shop.crawler.util.StringUtils;

public class ProductCrawlerTest {
  private static final String PRODUCT_INSERT_START_LINE_SQL =
      """
INSERT INTO bussiness.product (product_no,sku,"name",slug,unit_price,unit_listed_price,thumbnail,units_in_stock,ref,category_id) VALUES
""";
  private static final Random RANDOM = new Random();
  private static final String PRODUCT_CSS = "[class^=\"styles__ProductItemContainerStyled\"";
  private static final String PRODUCT_NAME_CSS = "[class^=\"style__NameStyled\"]";
  private static final String PRODUCT_PRICE_CSS = ".price-discount__price";
  private static final String PRODUCT_PRICE_DISCOUNT_PERCENT_CSS =
      "[class^=\"style__DiscountPercentStyled\"]";
  private static final String PRODUCT_THUMBNAIL_CSS = "picture.webpimg-container > img";
  private static final String PRODUCT_LINK_CSS = "[class^=\"style__ProductLink\"";

  private long categoryId = 1L;
  private String crawlLink;

  private String sqlOutFile;

  private static ChromeDriver driver;

  @BeforeAll
  static void setUp() {
    driver = new ChromeDriver();
  }

  @Test
  void crawlTikiProducts() throws InterruptedException {
    categoryId = 1L; // modify when run
    crawlLink = "https://tiki.vn/thiet-bi-kts-phu-kien-so/c1815"; // modify when run
    sqlOutFile = "V07__insert_into_product_table.sql"; // modify when run

    final List<Product> products = new LinkedList<>();
    driver.get(crawlLink);
    Thread.sleep(5_000);

    final List<WebElement> productElems = driver.findElements(By.cssSelector(PRODUCT_CSS));

    productElems.forEach(
        pE -> {
          final String name = pE.findElement(By.cssSelector(PRODUCT_NAME_CSS)).getText();

          // current price
          String priceStr = pE.findElement(By.cssSelector(PRODUCT_PRICE_CSS)).getText();
          priceStr = removeAllNonDigitCharacters(priceStr);
          final double price = priceStr.isBlank() ? 0D : Double.parseDouble(priceStr);

          // listed price
          String priceDiscountPercentStr = "0";
          try {
            priceDiscountPercentStr =
                pE.findElement(By.cssSelector(PRODUCT_PRICE_DISCOUNT_PERCENT_CSS)).getText();
          } catch (NoSuchElementException e) {
          }
          priceDiscountPercentStr = removeAllNonDigitCharacters(priceDiscountPercentStr);
          double priceDiscount =
              priceDiscountPercentStr.isBlank()
                  ? 0D
                  : (Double.parseDouble(priceDiscountPercentStr) / 100D);
          double listedPrice = (long) (price / (1 - priceDiscount));
          listedPrice = listedPrice - (listedPrice % 1_000);

          // thumbnail
          String thumbnail =
              pE.findElement(By.cssSelector(PRODUCT_THUMBNAIL_CSS)).getAttribute("srcset");
          thumbnail =
              thumbnail == null ? thumbnail : thumbnail.substring(0, thumbnail.indexOf(" "));

          // link
          final String ref = pE.findElement(By.cssSelector(PRODUCT_LINK_CSS)).getAttribute("href");

          // sku
          final String sku = RANDOM.nextInt(2010, 2025) + StringUtils.makeRandomString(10);

          // final String slug
          final String slug =
              StringUtils.removeAccent(name)
                      .replaceAll(
                          "[^a-zA-Z\\s]",
                          "") // replace all character which is not in [a-z, A-Z, space]
                      .replaceAll(" ", "-")
                      .toLowerCase()
                  + "-"
                  + StringUtils.makeRandomDigits(8);

          final String productNo = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + StringUtils.makeRandomDigits(4);

          final Product product =
              Product.builder()
                  .productNo(productNo)
                  .name(name)
                  .slug(slug)
                  .sku(sku)
                  .unitPrice(new BigDecimal(price))
                  .unitListedPrice(new BigDecimal(listedPrice))
                  .thumbnail(thumbnail)
                  .unitsInStock(RANDOM.nextLong(1_000))
                  .ref(ref)
                  .build();
          if (Objects.nonNull(product.getName()) && !product.getName().isBlank()) {
            products.add(product);
          }
        });
    writeInsertProductsSql(products);
    driver.quit();
  }

  private void writeInsertProductsSql(List<Product> products) {
    try (FileWriter fileWriter = new FileWriter(sqlOutFile)) {
      fileWriter.append(PRODUCT_INSERT_START_LINE_SQL);
      final int n = products.size();
      for (int i = 0; i < n; i++) {
        final Product product = products.get(i);
        String pattern = "('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')";
        pattern = i == n - 1 ? pattern + ";" : pattern + ",\n";
        final String productSql =
            pattern.formatted(
                product.getProductNo(),
                product.getSku(),
                product.getName().replaceAll("'", "''"),
                product.getSlug(),
                product.getUnitPrice(),
                product.getUnitListedPrice(),
                product.getThumbnail(),
                product.getUnitsInStock(),
                product.getRef(),
                categoryId);
        fileWriter.append(productSql);
      }
      fileWriter.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String removeAllNonDigitCharacters(String text) {
    final StringBuilder result = new StringBuilder();
    if (Objects.nonNull(text)) {
      final int n = text.length();
      for (int i = 0; i < n; i++) {
        final char c = text.charAt(i);
        if (Character.isDigit(c)) {
          result.append(c);
        }
      }
    }
    return result.toString();
  }
}
