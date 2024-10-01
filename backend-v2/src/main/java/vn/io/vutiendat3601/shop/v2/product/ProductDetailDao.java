package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;

import org.springframework.data.domain.Page;

public interface ProductDetailDao {

  Page<Product> selectAll(int page, int size);

  Optional<Product> selectProducDetail(String productNo);
}
