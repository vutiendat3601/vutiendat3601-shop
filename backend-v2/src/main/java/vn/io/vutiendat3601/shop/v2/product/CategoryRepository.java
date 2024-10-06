package vn.io.vutiendat3601.shop.v2.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  boolean existsCategoryByCode(String code);
  Optional<Category> findByCode(String code);
  void deleteByCode(String code);
}
