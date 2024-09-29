package vn.io.vutiendat3601.shop.v2.product;

import java.util.List;
import java.util.Optional;

public interface ProductDetailDao {

  List<Product> selectAll();

  Optional<Product> selectProducDetail(String productNo);
}
