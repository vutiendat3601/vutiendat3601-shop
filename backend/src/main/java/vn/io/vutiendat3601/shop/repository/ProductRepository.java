package vn.io.vutiendat3601.shop.repository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import vn.io.vutiendat3601.shop.entity.Product;

@CrossOrigin
public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

  Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);
}
