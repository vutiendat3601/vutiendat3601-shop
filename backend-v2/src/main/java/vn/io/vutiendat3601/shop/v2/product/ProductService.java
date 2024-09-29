package vn.io.vutiendat3601.shop.v2.product;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vn.io.vutiendat3601.shop.v2.exception.ConflictException;
import vn.io.vutiendat3601.shop.v2.exception.RequestValidationException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceDuplicationException;
import vn.io.vutiendat3601.shop.v2.exception.ResourceNotFoundException;
import vn.io.vutiendat3601.shop.v2.util.StringUtils;

@RequiredArgsConstructor
@Service
public class ProductService {
  private final ProductDao productDao;
  private final CategoryDao categoryDao;
  private final ProductDtoMapper productDtoMapper;
  private final PriceHistoryDao priceHistoryDao;

  @NonNull
  public List<ProductDto> getProductsByCategoryId(long categoryId) {
    final List<Product> products = productDao.selectByCategoryId(categoryId);
    return products.stream().map(productDtoMapper).toList();
  }

  public Product getProduct(String productNo) {
    return productDao
        .selectByProductNo(productNo)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Product with product_no [%s] not found".formatted(productNo)));
  }

  public void createProduct(CreateProductRequest createProductReq) {
    final Category category =
        categoryDao
            .selectById(createProductReq.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    if (productDao.existsProductBySku(createProductReq.sku())) {
      throw new ResourceDuplicationException("Sku already taken".formatted(createProductReq.sku()));
    }
    if (productDao.existsProductBySlug(createProductReq.slug())) {
      throw new ResourceDuplicationException(
          "Product slug already taken".formatted(createProductReq.slug()));
    }
    final String productNo =
        ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            + StringUtils.makeRandomDigits(4);
    final Product product =
        Product.builder()
            .sku(createProductReq.sku())
            .productNo(productNo)
            .slug(createProductReq.slug())
            .name(createProductReq.name())
            .description(createProductReq.description())
            .unitPrice(createProductReq.unitPrice())
            .unitListedPrice(createProductReq.unitListedPrice())
            .thumbnail(createProductReq.thumbnail())
            .category(category)
            .build();
    productDao.insertProduct(product);
  }

  @Transactional
  public void updateProduct(String productNo, UpdateProductRequest updateProductReq) {
    Product product = getProduct(productNo);
    boolean isChange = false;
    final Category category =
        categoryDao
            .selectById(updateProductReq.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    if (updateProductReq.sku() != null && !updateProductReq.sku().equals(product.getSku())) {
      product.setSku(updateProductReq.sku());
      isChange = true;
    }
    if (updateProductReq.slug() != null && !updateProductReq.slug().equals(product.getSlug())) {
      product.setSlug(updateProductReq.slug());
      isChange = true;
    }
    if (updateProductReq.name() != null && !updateProductReq.name().equals(product.getName())) {
      product.setName(updateProductReq.name());
      isChange = true;
    }
    if (updateProductReq.description() != null
        && !updateProductReq.description().equals(product.getDescription())) {
      product.setDescription(updateProductReq.description());
      isChange = true;
    }

    if (updateProductReq.thumbnail() != null
        && !updateProductReq.thumbnail().equals(product.getThumbnail())) {
      product.setThumbnail(updateProductReq.thumbnail());
      isChange = true;
    }
    if (updateProductReq.isActive() != null
        && !updateProductReq.isActive().equals(product.getIsActive())) {
      product.setIsActive(updateProductReq.isActive());
      isChange = true;
    }
    if (category.getId() != null && category.getId() != product.getCategory().getId()) {
      product.setCategory(category);
      isChange = true;
    }
    if (updateProductReq.unitPrice() != null
        && !updateProductReq.unitPrice().equals(product.getUnitPrice())) {
      product.setUnitPrice(updateProductReq.unitPrice());
      isChange = true;
    }
    if (!isChange) {
      throw new RequestValidationException("No data changes found");
    }

    productDao.updateProduct(product);
  }

  @Transactional
  public void updateUnitPrice(String productNo, BigDecimal newUnitPrice) {
    Product product = getProduct(productNo);

    if (newUnitPrice == null) {
      throw new IllegalArgumentException("The 'new unit price' cannot be null");
    }
    if (newUnitPrice.equals(product.getUnitPrice())) {
      throw new RequestValidationException("The 'unit price' is the same as the current price");
    }

    if (newUnitPrice.compareTo(product.getUnitListedPrice()) > 0) {
      throw new ConflictException("The 'unit price' must be less than or equal to 'listed price'");
    }
    inserPriceHistory(newUnitPrice, productNo);

    product.setUnitPrice(newUnitPrice);
    productDao.updateProduct(product);
  }

  @Transactional
  public void deleteProduct(String productNo) {
    if (!productDao.existsProductByProductNo(productNo)) {
      throw new ResourceNotFoundException("Product with id [%s] not found".formatted(productNo));
    }
    productDao.deleteProduct(productNo);
  }

  private void inserPriceHistory(BigDecimal toPrice, String productNo) {
    Product product =
        productDao
            .selectByProductNo(productNo)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Product with product_no [%s] not found".formatted(productNo)));

    if (toPrice.compareTo(product.getUnitListedPrice()) > 0) {
      throw new ConflictException("The 'unit price' must be less than or equal to 'listed price'");
    }

    final PriceHistory priceHistory =
        PriceHistory.builder().price(toPrice).product(product).build();

    priceHistoryDao.insert(priceHistory);
  }
}
