package vn.io.vutiendat3601.shop.v2.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  Long findInventoryIdByProductId(Long id);
}
